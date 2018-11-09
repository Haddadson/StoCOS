package com.stocos.lote;

import java.time.LocalDateTime;
import java.util.UUID;

import org.json.JSONObject;

import com.stocos.dao.DefaultDaoImpl;

public class LoteDao extends DefaultDaoImpl<Lote> {

	public LoteDao() {

	}

	@Override
	public JSONObject toJson(Lote lote) {
		JSONObject json = new JSONObject();
		json.put("id-produto", lote.getIdProduto());
		json.put("quantidade", lote.getQuantidade());
		json.put("validade", lote.getValidade());
		return json;
	}

	@Override
	public Lote fromJson(JSONObject json) {
		int quantidade = json.getInt("quantidade");
		UUID idProduto = UUID.fromString(json.getString("id-produto"));
		LocalDateTime validade = LocalDateTime.parse(json.getString("validade"));
		return new Lote(idProduto, validade, quantidade);
	}

	@Override
	public String getCaminho() {
		return "data/lote.txt";
	}
}
