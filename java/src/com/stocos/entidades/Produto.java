package com.stocos.entidades;

import org.json.JSONObject;

import com.stocos.json.JsonFormatter;

public class Produto implements JsonFormatter {

	private static int ID = 0; // Contador de IDs
	private int id; // id Individual

	private String nome, marca, categoria;
	private double volume;
	public int quantidade;

	public Produto(String nome, String marca, String categoria, int quantidade, double volume) {
		setNome(nome);
		setMarca(marca);
		setCategoria(categoria);
		setVolume(volume);
		quantidade = 1;
		id = ID++;
	}

	public int getId() {
		return id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public void aumentarQuantidade(int qnt) {
		if (qnt > 0)
			quantidade += qnt;
	}

	public void diminuirQuantidade(int qnt) {
		if (qnt > 0 && quantidade - qnt >= 0)
			quantidade -= qnt;
	}

	public String getNome() {
		return nome;
	}

	public String getMarca() {
		return marca;
	}

	public String getCategoria() {
		return categoria;
	}

	public double getVolume() {
		return volume;
	}

	public int getQuantidade() {
		return quantidade;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Produto) {
			Produto p = (Produto) obj;
			if (p.getNome() == getNome() && p.getMarca() == getMarca() && p.getCategoria() == getCategoria())
				return true;
			else
				return false;
		}
		return false;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		obj.put("nome", getNome());
		obj.put("marca", getMarca());
		obj.put("categoria", getCategoria());
		obj.put("volume", getVolume());
		return null;
	}
}