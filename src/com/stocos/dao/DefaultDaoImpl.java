package com.stocos.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;

public abstract class DefaultDaoImpl<O> implements Dao<UUID, O> {

	public static final String CAMPO_UUID = "uuid";

	public abstract JSONObject toJson(O obj);

	public abstract O fromJson(JSONObject obj);

	public abstract String getCaminho();

	public DefaultDaoImpl() {
		createFile();
		resetUnique();
	}

	private void createFile() {
		try {
			File file = new File(getCaminho());
			file.createNewFile();
		} catch (Exception e) {
			System.err.println("Erro ao criar arquivo " + getCaminho() + ": " + e.getMessage());
		}
	}

	//

	private Set<String> atributosUnicos;

	protected void setUnique(String atr) {
		atributosUnicos.add(atr);
	}

	protected void removeUnique(String atr) {
		atributosUnicos.remove(atr);
	}

	protected void resetUnique() {
		atributosUnicos = new HashSet<>();
	}

	//

	private synchronized BufferedReader getReader() {
		try {
			return Files.newBufferedReader(Paths.get(getCaminho()));
		} catch (IOException e) {
			throw new RuntimeException("Houver um erro ao criar o BufferedReader de " + getCaminho(), e);
		}
	}

	private synchronized void writeLines(List<String> lines, boolean append) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(getCaminho(), append))) {
			lines.forEach(pw::println);
		} catch (IOException e) {
			throw new RuntimeException("Houver um erro ao escrever no arquivo " + getCaminho(), e);
		}
	}

	@Override
	public synchronized Map<UUID, O> getByAtributo(String atr, Object valor) {
		return getReader().lines().map(JSONObject::new).filter(json -> {
			return json.has(atr) && json.get(atr).equals(valor);
		}).map(e -> jsonToEntry(e)).collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	@Override
	public synchronized Map<UUID, O> getAll() {
		return getReader() //
				.lines() //
				.map(JSONObject::new) //
				.map(e -> jsonToEntry(e)) //
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	@Override
	public synchronized Entry<UUID, O> getById(UUID id) {
		return getReader() //
				.lines() //
				.map(JSONObject::new) //
				.map(e -> jsonToEntry(e)) //
				.filter(e -> e.getKey().equals(id)) //
				.findAny().get();
	}

	@Override
	public synchronized void create(O novoObj) {
		Map<UUID, O> map = getAll();
		if (contarConflitos(map, novoObj) == 0) {
			UUID uuid = UUID.randomUUID();
			JSONObject json = toJson(novoObj).put(CAMPO_UUID, uuid);
			writeLines(Arrays.asList(json.toString()), true);
		}
	}

	@Override
	public synchronized void update(UUID id, O novoObj) {
		Map<UUID, O> map = getAll();
		if (contarConflitos(map, novoObj) == 0 && map.containsKey(id)) {
			map.put(id, novoObj);
			writeLines(map.entrySet() //
					.stream() //
					.map(e -> entryToJson(e)) //
					.map(json -> json.toString()) //
					.collect(Collectors.toList()), false);
		}
	}

	@Override
	public synchronized void delete(UUID id) {
		Map<UUID, O> map = getAll();
		if (map.containsKey(id)) {
			map.remove(id);
			writeLines(map.entrySet() //
					.stream() //
					.map(e -> entryToJson(e)) //
					.map(json -> json.toString()) //
					.collect(Collectors.toList()), false);
		}
	}

	public JSONObject entryToJson(Entry<UUID, O> e) {
		if (e == null)
			return new JSONObject();

		JSONObject json = toJson(e.getValue());
		json.put(CAMPO_UUID, e.getKey());
		return json;
	}

	public Entry<UUID, O> jsonToEntry(JSONObject json) {
		if (json == null)
			return new SimpleEntry<UUID, O>(null, null);

		O obj = fromJson(json);
		UUID uuid = UUID.fromString(json.getString(CAMPO_UUID));
		return new SimpleEntry<UUID, O>(uuid, obj);
	}

	private long contarConflitos(Map<UUID, O> map, O obj) {
		JSONObject target = toJson(obj);
		return map.entrySet().stream().map(e -> entryToJson(e)).filter(json -> {
			for (String atr : atributosUnicos) {
				Object jsonValue = json.has(atr) ? json.get(atr) : "";
				Object targetValue = target.has(atr) ? target.get(atr) : "";
				if (jsonValue.equals(targetValue) && !jsonValue.toString().isEmpty())
					return true;
			}
			return false;
		}).count();
	}

}
