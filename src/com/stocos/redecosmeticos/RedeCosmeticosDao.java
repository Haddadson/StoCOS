package com.stocos.redecosmeticos;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;

import com.stocos.dao.DefaultDaoImpl;

public class RedeCosmeticosDao extends DefaultDaoImpl<RedeCosmeticos> {

	public RedeCosmeticosDao() {
		setUnique("nome");
		setUnique("email");
	}

	@Override
	public JSONObject toJson(RedeCosmeticos obj) {
		JSONObject json = new JSONObject() {
			@Override
			public JSONObject put(String atr, Object val) {
				return super.put(atr, val == null ? new String("") : val);
			}
		};
		json.put("nome", obj.getNome());
		json.put("email", obj.getEmail());
		json.put("endereco", obj.getEndereco());
		json.put("telefone", obj.getTelefone());
		json.put("capacidade", obj.getCapacidade());
		return json;
	}

	@Override
	public RedeCosmeticos fromJson(JSONObject json) {
		String nome = json.getString("nome");
		String email = json.getString("email");
		String endereco = json.getString("endereco");
		String telefone = json.getString("telefone");
		double capacidade = json.getDouble("capacidade");

		RedeCosmeticos r = new RedeCosmeticos(nome, capacidade);
		r.setEmail(email);
		r.setEndereco(endereco);
		r.setTelefone(telefone);
		return r;
	}

	@Override
	public String getCaminho() {
		return "data/redecosmeticos.txt";
	}

	// GETTERS ESPECIFICOS DA REDECOSMETICOSDAO

	public Entry<UUID, RedeCosmeticos> getByNome(String nome) {
		Optional<Entry<UUID, RedeCosmeticos>> opt = getByAtributo("nome", nome).entrySet().stream().findAny();
		if (opt.isPresent())
			return opt.get();
		return null;
	}

	public Entry<UUID, RedeCosmeticos> getByEmail(String email) {
		Optional<Entry<UUID, RedeCosmeticos>> opt = getByAtributo("email", email).entrySet().stream().findAny();
		if (opt.isPresent())
			return opt.get();
		return null;
	}

}
