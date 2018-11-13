package com.stocos.produto;

import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;

import com.stocos.dao.DefaultDaoImpl;

public class ProdutoDao extends DefaultDaoImpl<Produto> {

	public ProdutoDao() {
		setUnique("nome");
	}

	@Override
	public JSONObject toJson(Produto obj) {
		JSONObject json = new JSONObject() {
			@Override
			public JSONObject put(String atr, Object val) {
				return super.put(atr, val == null ? new String("") : val);
			}
		};
		json.put("nome", obj.getNome());
		json.put("marca", obj.getMarca());
		json.put("categoria", obj.getCategoria());
		json.put("volume", obj.getVolume());
		return json;
	}

	@Override
	public Produto fromJson(JSONObject json) {
		String nome = json.getString("nome");
		String marca = json.getString("marca");
		String categoria = json.getString("categoria");
		double volume = json.getDouble("volume");
		Produto p = new Produto(nome, volume);
		p.setMarca(marca);
		p.setCategoria(categoria);
		return p;
	}

	@Override
	public String getCaminho() {
		return "data/produto.txt";
	}

	// GETTERS EXCLUSIVOS DO PRODUTODAO

	public Entry<UUID, Produto> getByNome(String nome) {
		Optional<Entry<UUID, Produto>> opt = getByAtributo("nome", nome).entrySet().stream().findAny();
		if (opt.isPresent())
			return opt.get();
		return null;
	}

}
