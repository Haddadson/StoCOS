package com.stocos.services;

import org.json.JSONArray;
import org.simpleframework.http.Query;

import com.stocos.entidades.Estoque;
import com.stocos.entidades.Produto;
import com.stocos.entidades.Setor;

public class ProdutoService implements IServico {

	@Override
	public String get(Query query) {
		return null;
	}

	@Override
	public String getAll(Query query) {
		try {
			Setor setor = Estoque.getInstance().getSetor(query.get("nomerede"));
			JSONArray arr = new JSONArray();
			for (Produto p : setor.getListaProdutos()) {
				arr.put(p.toJson());
			}
			return arr.toString();
		} catch (Exception e) {
			return "{status: ERRO}";
		}
	}

	@Override
	public String add(Query query) {
		return null;
	}

	@Override
	public String delete(Query query) {
		return null;
	}

	@Override
	public String modificar(Query query) {
		return null;
	}

}