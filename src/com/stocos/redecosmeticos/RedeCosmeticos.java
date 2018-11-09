package com.stocos.redecosmeticos;

public class RedeCosmeticos {

	private String nome, email, endereco, telefone;
	private double capacidade;

	public RedeCosmeticos(String nome, double capacidade) {
		setNome(nome);
		setCapacidade(capacidade);
	}

	//

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setCapacidade(double capacidade) {
		this.capacidade = capacidade;
	}

	//

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public double getCapacidade() {
		return capacidade;
	}

}
