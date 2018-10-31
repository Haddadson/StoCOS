package com.stocos.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;

import com.stocos.entidades.Estoque;
import com.stocos.entidades.Produto;
import com.stocos.entidades.Setor;

public class ProdutoService implements IServico {

	@Override
	public String get(Query query) {
		try {
			String nomeRede = query.get("nomerede");
			String nomeProduto = query.get("nomeproduto");
			String marca = query.get("marca");
			String categoria = query.get("categoria");
			Produto p = Estoque.getInstance() //
					.getSetor(nomeRede) //
					.getProduto(nomeProduto, marca, categoria);
			if (p != null)
				return p.toJson().toString();
			else
				return new JSONObject().put("status", "NAO ENCONTRADO").toString();
		} catch (Exception e) {
			return new JSONObject().put("status", "ERRO: " + e.getMessage()).toString();
		}
	}

	@Override
	public String getAll(Query query) {
		try {
			Setor setor = Estoque.getInstance().getSetor(query.get("nomerede"));
			JSONArray arr = new JSONArray();
			for (Produto p : setor.getListaProdutos())
				arr.put(p.toJson());
			return arr.toString();
		} catch (Exception e) {
			return new JSONObject().put("status", "ERRO: " + e.getMessage()).toString();
		}
	}

	@Override
	public String add(Query query) {
		try {
			String nomeRede = query.get("nomerede");
			String nome = query.get("nome");
			String marca = query.get("marca");
			String categoria = query.get("categoria");
			String volume = query.get("volume");
			String quantidade = query.get("quantidade");

			// Não é obrigatório informar a quantidade:
			if (quantidade == null)
				quantidade = "1";

			Produto p = new Produto(nome, marca, categoria, Integer.parseInt(quantidade), Double.parseDouble(volume));
			if (Estoque.getInstance().getSetor(nomeRede).adicionarProdutos(p))
				return new JSONObject().put("status", "ADICIONADO").toString();
			else
				return new JSONObject().put("status", "ERRO AO ADICIONAR").toString();
		} catch (Exception e) {
			return new JSONObject().put("status", "ERRO: " + e.getMessage()).toString();
		}
	}

	@Override
	public String remover(Query query) {
		try {
			String nomeRede = query.get("nomerede");
			String idProduto = query.get("idproduto");
			String quantidade = query.get("quantidade");

			if (Estoque.getInstance().getSetor(nomeRede).removerProdutos(Integer.parseInt(idProduto),
					Integer.parseInt(quantidade))) {
				return new JSONObject().put("status", "REMOVIDO").toString();
			} else {
				return new JSONObject().put("status", "ERRO AO REMOVER").toString();
			}
		} catch (Exception e) {
			return new JSONObject().put("status", "ERRO: " + e.getMessage()).toString();
		}
	}

	@Override
	public String alterar(Query query) {
		return new JSONObject().put("status", "INDISPONIVEL").toString();
	}

}