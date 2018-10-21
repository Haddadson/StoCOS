package com.stocos.services;

import org.json.JSONArray;
import org.json.JSONObject;
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
	public String alterar(Query query) {
		System.out.println(query);
		try {
			String nomeRede = query.get("nomerede");
			double novaCapacidade = Double.parseDouble(query.get("novacapacidade"));
			if (novaCapacidade < 0)
				return new JSONObject().put("status", "ERRO: Nova capacidade negativa.").toString();

			Setor setor = Estoque.getInstance().getSetor(nomeRede);
			double diferenca = setor.getCapacidade() - novaCapacidade;
			if (diferenca > 0) {
				setor.reduzir(diferenca);
				return new JSONObject().put("status", "CAPACIDADE REDUZIDA").toString();
			} else {
				setor.expandir(-1.0 * diferenca);
				return new JSONObject().put("status", "CAPACIDADE EXPANDIDA").toString();
			}
		} catch (Exception e) {
			return new JSONObject().put("status", "ERRO: " + e.getMessage()).toString();
		}
	}

}
