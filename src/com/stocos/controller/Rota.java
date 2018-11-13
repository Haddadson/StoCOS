package com.stocos.controller;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public interface Rota {

	String execute(Request req, Response res) throws Exception;

}
