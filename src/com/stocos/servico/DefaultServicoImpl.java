package com.stocos.servico;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.json.JSONArray;

import com.stocos.dao.DefaultDaoImpl;

public class DefaultServicoImpl<O> implements Servico<UUID, O> {

	private DefaultDaoImpl<O> dao;

	public DefaultServicoImpl(DefaultDaoImpl<O> dao) {
		this.dao = dao;
	}

	@Override
	public String getAll() {
		Map<UUID, O> map = dao.getAll();
		JSONArray arr = new JSONArray();
		map.entrySet().stream().map(dao::entryToJson).forEach(arr::put);
		return arr.toString();
	}

	@Override
	public String getById(String uuid) {
		Entry<UUID, O> e = dao.getById(UUID.fromString(uuid));
		JSONArray arr = new JSONArray();
		arr.put(dao.entryToJson(e));
		return arr.toString();
	}

	@Override
	public String getByAtributo(String atr, String valor) {
		Map<UUID, O> map = dao.getByAtributo(atr, valor);
		JSONArray arr = new JSONArray();
		map.entrySet().stream().map(dao::entryToJson).forEach(arr::put);
		return arr.toString();
	}

	@Override
	public String add(O obj) {
		dao.create(obj);
		return "";
	}

	@Override
	public String update(UUID k, O obj) {
		dao.update(k, obj);
		return "";
	}

	@Override
	public String delete(UUID k) {
		dao.delete(k);
		return "";
	}
}
