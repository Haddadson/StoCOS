package com.stocos.entidades;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.stocos.json.JsonFormatter;

public class Estoque implements JsonFormatter {

	private static Estoque INSTANCE;

	public static Estoque getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Estoque();
		return INSTANCE;
	}

	private List<Setor> setores;

	private Estoque() {
		setores = new ArrayList<Setor>();
	}

	public Setor getSetor(String nomeRede) {
		for (Setor s : setores)
			if (s.getRedeCosmeticos().getNome().equalsIgnoreCase(nomeRede))
				return s;

		return null;
	}

	public List<Setor> getListaSetores() {
		return setores;
	}

	public int getNumSetores() {
		return setores.size();
	}

	public boolean adicionarSetor(Setor setor) {
		for (Setor s : setores)
			if (s.getRedeCosmeticos().getNome().equalsIgnoreCase(setor.getRedeCosmeticos().getNome()))
				return false;

		return setores.add(setor);
	}

	public boolean removerSetor(Setor setor) {
		return setores.remove(setor);
	}

	public double getCapacidadeTotal() {
		double cap = 0;
		for (Setor s : setores)
			cap += s.getCapacidade();
		return cap;
	}

	public double getOcupacaoTotal() {
		double ocup = 0;
		for (Setor s : setores)
			ocup += s.calcularOcupacao();
		return ocup;
	}

	public int getNumProdutos() {
		int qnt = 0;
		for (Setor s : setores)
			qnt += s.calcularNumProdutos();
		return qnt;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("numeroSetores", getNumSetores());
		obj.put("capacidade", getCapacidadeTotal());
		obj.put("ocupacao", getOcupacaoTotal());
		obj.put("numProdutos", getNumProdutos());
		return obj;
	}
}
