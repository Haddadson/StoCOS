package com.stocos.entidades;

public class Setor {

	private static int ID = 0;

	private int id;
	private float capacidade;
	private RedeCosmeticos dono;

	public Setor(float capacidade, RedeCosmeticos dono) {
		setCapacidade(capacidade);
		setDono(dono);
		id = ID++;
	}

	public void setCapacidade(float capacidade) {
		this.capacidade = capacidade;
	}

	public float getCapacidade() {
		return capacidade;
	}

	public RedeCosmeticos getDono() {
		return dono;
	}

	private void setDono(RedeCosmeticos rede) {
		this.dono = rede;
	}

	public int getId() {
		return id;
	}
}
