package com.stocos.entidades;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.stocos.json.JsonFormatter;

public class Setor implements JsonFormatter {

	private static int ID = 0;
	private int id;

	private double capacidade;
	private List<Produto> produtos;
	private RedeCosmeticos redeCosmeticos;

	public Setor(RedeCosmeticos rede, double capacidade) {
		produtos = new ArrayList<>();
		redeCosmeticos = rede;
		this.capacidade = capacidade;
		id = ID++;
	}

	public int getId() {
		return id;
	}

	public double getCapacidade() {
		return capacidade;
	}

	public List<Produto> getListaProdutos() {
		return produtos;
	}

	public RedeCosmeticos getRedeCosmeticos() {
		return redeCosmeticos;
	}

	public int getNumProdutos() {
		int qnt = 0;
		for (Produto p : produtos) {
			qnt += p.getQuantidade();
		}
		return qnt;
	}

	public double getOcupacao() {
		double c = 0;
		for (Produto p : produtos)
			c += p.getVolume() * p.getQuantidade();
		return c;
	}

	public void adicionarProduto(Produto p) {
		double ocupacao = getOcupacao();
		if (produtos.contains(p) && p.getVolume() * p.getQuantidade() + ocupacao <= capacidade) {
			produtos.get(produtos.indexOf(p)).aumentarQuantidade(p.getQuantidade());
		} else if (p.getVolume() + ocupacao <= capacidade)
			produtos.add(p);
	}

	public void removerProduto(Produto p) {
		if (produtos.contains(p)) {
			Produto pLista = produtos.get(produtos.indexOf(p));
			if (pLista.getQuantidade() > 1)
				pLista.diminuirQuantidade(p.getQuantidade());
			else
				produtos.remove(pLista);
		}
	}

	public void reduzir(double qnt) {
		if (capacidade - qnt >= getOcupacao())
			capacidade -= qnt;
	}

	public void expandir(double qnt) {
		capacidade += qnt;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		obj.put("redeCosmetico", getRedeCosmeticos().getNome());
		obj.put("redeCosmeticoId", getRedeCosmeticos().getId());
		obj.put("capacidade", getCapacidade());
		obj.put("ocupacao", getOcupacao());
		return obj;
	}
}
