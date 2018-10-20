package com.stocos.entidades;

import java.util.HashSet;
import java.util.Set;

public class Estoque {
	private static Estoque INSTANCE;

	public static Estoque getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Estoque();
		return INSTANCE;
	}

	private Set<Setor> setores;

	private Estoque() {
		setores = new HashSet<>();
	}

	public Set<Setor> getSetores() {
		return setores;
	}
}
