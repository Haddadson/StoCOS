package com.stocos.services;

import org.json.JSONArray;
import org.simpleframework.http.Query;

import com.stocos.entidades.Estoque;
import com.stocos.entidades.RedeCosmeticos;
import com.stocos.entidades.Setor;

public class RedeCosmeticosService implements IServico {

	@Override
	public String get(Query query) {
		try {
			return Estoque.getInstance() //
					.getSetor(query.get("nome")) //
					.getRedeCosmeticos() //
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
			arr.put(s.getRedeCosmeticos().toJson());
		}
		return arr.toString();
	}

	@Override
	public String add(Query query) {

		try {
			String nome = query.get("nome");
			String endereco = query.get("endereco");
			String telefone = query.get("telefone");
			String email = query.get("email");
			String capacidade = query.get("capacidade");
			RedeCosmeticos rede = new RedeCosmeticos(nome, endereco, email, telefone);
			Setor setor = new Setor(rede, Double.parseDouble(capacidade));
			if (Estoque.getInstance().adicionarSetor(setor))
				return "{status: OK}";
			else
				return "{status: DUPLICADO}";
		} catch (Exception e) {
			return "{status: ERRO}";
		}
	}

	@Override
	public String remover(Query query) {
		return null;
	}

	@Override
	public String alterar(Query query) {
		return null;
	}

}