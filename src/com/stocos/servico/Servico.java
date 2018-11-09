package com.stocos.servico;

public interface Servico<K, O> {

	String getAll();

	String getById(String id);

	String getByAtributo(String atr, String valor);

	String add(O obj);
	
	String update(K k, O obj);
	
	String delete(K k);

}
