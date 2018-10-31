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
		setRedeCosmeticos(rede);
		setCapacidade(capacidade);
		id = ID++;
	}

	private void setRedeCosmeticos(RedeCosmeticos rede) {
		this.redeCosmeticos = rede;
	}

	private void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}

	public boolean reduzir(double qnt) {
		if (qnt > 0 && getCapacidade() - qnt >= calcularOcupacao()) {
			setCapacidade(getCapacidade() - qnt);
			return true;
		}
		return false;
	}

	public boolean expandir(double qnt) {
		if (qnt > 0) {
			setCapacidade(getCapacidade() + qnt);
			return true;
		}
		return false;
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

	public Produto getProduto(String nome, String marca, String categoria) {
		for (Produto p : produtos) {
			if (p.getNome().equalsIgnoreCase(nome) && p.getMarca().equalsIgnoreCase(marca)
					&& p.getCategoria().equalsIgnoreCase(categoria))
				return p;
		}
		return null;
	}

	public RedeCosmeticos getRedeCosmeticos() {
		return redeCosmeticos;
	}

	public int calcularNumProdutos() {
		int qnt = 0;
		for (Produto p : produtos)
			qnt += p.getQuantidade();
		return qnt;
	}

	public double calcularOcupacao() {
		double c = 0;
		for (Produto p : produtos)
			c += p.getQuantidade() * p.getVolume();
		return c;
	}

	public boolean adicionarProdutos(Produto p) {
		double ocupacao = calcularOcupacao();
		if (produtos.contains(p) && ocupacao + (p.getVolume() * p.getQuantidade()) <= getCapacidade())
			return produtos.get(produtos.indexOf(p)).aumentarQuantidade(p.getQuantidade());
		else if (ocupacao + (p.getVolume() * p.getQuantidade()) <= getCapacidade())
			return produtos.add(p);

		return false;
	}

	public boolean removerProdutos(Produto p) {
		if (produtos.contains(p)) {
			Produto pLista = produtos.get(produtos.indexOf(p));
			if (pLista.getQuantidade() - p.getQuantidade() > 0)
				return pLista.diminuirQuantidade(p.getQuantidade());
			else if (pLista.getQuantidade() - p.getQuantidade() == 0)
				return produtos.remove(pLista);
		}
		return false;
	}

	public Produto getProdutoById(int id) {
		for (Produto p : produtos) {
			if (p.getId() == id)
				return p;
		}
		return null;
	}

	public boolean removerProdutos(int idProduto, int qnt) {
		Produto p = getProdutoById(idProduto);
		if (p != null) {
			if (p.getQuantidade() - qnt > 0)
				return p.diminuirQuantidade(qnt);
			else if (p.getQuantidade() - qnt == 0)
				return produtos.remove(p);
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Setor) {
			Setor s = (Setor) obj;
			return s.getRedeCosmeticos().getNome().equals(getRedeCosmeticos().getNome());
		}
		return false;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		obj.put("redeCosmetico", getRedeCosmeticos().getNome());
		obj.put("redeCosmeticoId", getRedeCosmeticos().getId());
		obj.put("numProdutos", calcularNumProdutos());
		obj.put("capacidade", getCapacidade());
		obj.put("ocupacao", calcularOcupacao());
		return obj;
	}
}
