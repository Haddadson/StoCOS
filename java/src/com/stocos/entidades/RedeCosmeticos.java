package com.stocos.entidades;

import org.json.JSONObject;

import com.stocos.json.JsonFormatter;

public class RedeCosmeticos implements JsonFormatter {

	private static int ID = 0;

	private int id;
	private String nome, endereco, email, telefone;

	private Setor setor;

	public RedeCosmeticos(String nome, String endereco, String email, String telefone) {
		setNome(nome);
		setEndereco(endereco);
		setEmail(email);
		setTelefone(telefone);
		id = ID++;
	}

	public int getId() {
		return id;
	}

	public void associarSetor(Setor setor) {
		this.setor = setor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getEmail() {
		return email;
	}

	public String getTelefone() {
		return telefone;
	}

	public Setor getSetor() {
		return setor;
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		obj.put("nome", getNome());
		obj.put("endereco", getEndereco());
		obj.put("telefone", getTelefone());
		obj.put("email", getEmail());
		if (getSetor() != null)
			obj.put("setorId", getSetor().getId());
		return obj;
	}
}
