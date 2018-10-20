package com.stocos.entidades;

public class RedeCosmeticos {

	private static int ID = 0;

	private String nome;
	private int id;
	private Setor setor;

	public RedeCosmeticos(String nome, Setor setor) {
		setNome(nome);
		setSetor(setor);
		id = ID++;
	}

	private void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	private void setSetor(Setor setor) {
		this.setor = setor;
	}

	public int getId() {
		return id;
	}
}
