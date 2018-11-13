package com.stocos.lote;

import java.time.LocalDate;
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
		json.put("status", lote.getStatus());
		json.put("id-rede", lote.getIdRede());
		json.put("id-produto", lote.getIdProduto());
		json.put("quantidade", lote.getQuantidade());
		json.put("data-validade", lote.getDataValidade());
		json.put("data-entrega", lote.getDataEntrega());
		json.put("data-agendamento", lote.getDataAgendamento());
		return json;
	}

	@Override
	public Lote fromJson(JSONObject json) {
		int status = json.getInt("status");
		UUID idRede = UUID.fromString(json.getString("id-rede"));
		UUID idProduto = UUID.fromString(json.getString("id-produto"));
		int quantidade = json.getInt("quantidade");
		LocalDateTime validade = LocalDateTime.parse(json.getString("data-validade"));
		LocalDate entrega = LocalDate.parse(json.getString("data-entrega"));
		LocalDate agendamento = LocalDate.parse(json.getString("data-agendamento"));
		return new Lote(idRede, agendamento, entrega, idProduto, validade, quantidade, status);
	}

	@Override
	public String getCaminho() {
		return "data/lote.txt";
	}
}
