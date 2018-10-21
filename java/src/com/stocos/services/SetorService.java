package com.stocos.services;

import org.json.JSONArray;
import org.simpleframework.http.Query;

import com.stocos.entidades.Estoque;
import com.stocos.entidades.Setor;

public class SetorService implements IServico {

	@Override
	public String get(Query query) {
		try {
			return Estoque.getInstance() //
					.getSetor(query.get("nome")) //
					.toJson() //
					.toString();
		} catch (Exception e) {
			return "{status: ERRO}";
		}
	}

	@Override
	public String getAll(Query query) {
		JSONArray arr = new JSONArray();
		for (Setor s : Estoque.getInstance().getListaSetores()) {
			arr.put(s.toJson());
		}
		return arr.toString();
	}

	@Override
	public String add(Query query) {
		return null;
	}

	@Override
	public String remover(Query query) {
		return null;
	}

	@Override
	public String modificar(Query query) {
		return null;
	}

}
