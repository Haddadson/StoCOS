package com.stocos.servico;

import java.util.Map.Entry;

import org.json.JSONObject;
import org.simpleframework.http.Query;

public interface Servico<K, O> {

	String getAll() throws Exception;

	String getById(Query query) throws Exception;

	String delete(Query query) throws Exception;

	String getByAtributo(Entry<String, String> entry) throws Exception;

	String add(JSONObject json) throws Exception;

	String update(JSONObject json) throws Exception;

}
