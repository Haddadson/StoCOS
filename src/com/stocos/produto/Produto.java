package com.stocos.produto;

public class Produto {

	private String rede, nome, marca, categoria;
	private double volume;

	public Produto(String rede, String nome, double volume) {
		setRedeCosmeticos(rede);
		setNome(nome);
		setVolume(volume);
	}

	//

	public void setRedeCosmeticos(String rede) {
		this.rede = rede;
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

	//

	public String getRedeCosmeticos() {
		return rede;
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

}
