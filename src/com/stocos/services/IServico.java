package com.stocos.services;

import org.simpleframework.http.Query;

public interface IServico {

	String get(Query query);

	String getAll(Query query);

	String add(Query query);

	String remover(Query query);

	String alterar(Query query);

}
