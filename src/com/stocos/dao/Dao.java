package com.stocos.dao;

import java.util.Map;
import java.util.Map.Entry;

public interface Dao<K, O> {

	Map<K, O> getByAtributo(String atr, Object valor);

	Map<K, O> getAll();

	Entry<K, O> getById(K id);

	void create(O obj);

	void update(K id, O obj);

	void delete(K id);

}
