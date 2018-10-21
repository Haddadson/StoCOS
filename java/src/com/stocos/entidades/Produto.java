package com.stocos.entidades;

import org.json.JSONObject;

import com.stocos.json.JsonFormatter;

public class Produto implements JsonFormatter {

	private static int ID = 0; // Contador global de IDs
	private int id; // id individual

	private String nome, marca, categoria;
	private double volume;
	private int quantidade;

	public Produto(String nome, String marca, String categoria, int quantidade, double volume) {
		setNome(nome);
		setMarca(marca);
		setCategoria(categoria);
		setVolume(volume > 0 ? volume : 1);
		setQuantidade(quantidade > 0 ? quantidade : 1);
		id = ID++;
	}

	public Produto(String nome, String marca, String categoria, double volume) {
		this(nome, marca, categoria, 1, volume);
	}

	public int getId() {
		return id;
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

	public int getQuantidade() {
		return quantidade;
	}

	public double getVolume() {
		return volume;
	}

	private void setQuantidade(int qnt) {
		quantidade = qnt;
	}

	private void setNome(String nome) {
		this.nome = nome;
	}

	private void setMarca(String marca) {
		this.marca = marca;
	}

	private void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	private void setVolume(double volume) {
		this.volume = volume;
	}

	public boolean aumentarQuantidade(int qnt) {
		if (qnt > 0) {
			quantidade += qnt;
			return true;
		}
		return false;
	}

	public boolean diminuirQuantidade(int qnt) {
		if (qnt > 0 && quantidade - qnt >= 0) {
			quantidade -= qnt;
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Produto) {
			Produto p = (Produto) obj;
			return p.getNome().equals(getNome()) //
					&& p.getMarca().equals(getMarca()) //
					&& p.getCategoria().equals(getCategoria());
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
		obj.put("quantidade", getQuantidade());
		obj.put("volume", getVolume());
		return obj;
	}
}