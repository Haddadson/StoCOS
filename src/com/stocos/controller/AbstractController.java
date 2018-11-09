package com.stocos.controller;

import java.io.PrintStream;
import java.util.function.BiFunction;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public abstract class AbstractController {

	private boolean respondido = false;
	private Request req;
	private Response res;

	public AbstractController(Request req, Response res) {
		this.req = req;
		this.res = res;
	}

	public Request getRequest() {
		return req;
	}

	public Response getResponse() {
		return res;
	}

	public abstract void handle() throws Exception;

	public void get(String path, BiFunction<Request, Response, String> f) throws Exception {
		executeFunction("GET", path, f);
	}

	public void post(String path, BiFunction<Request, Response, String> f) throws Exception {
		executeFunction("POST", path, f);
	}

	public void delete(String path, BiFunction<Request, Response, String> f) throws Exception {
		executeFunction("DELETE", path, f);
	}

	public void put(String path, BiFunction<Request, Response, String> f) throws Exception {
		executeFunction("PUT", path, f);
	}

	public void any(String path, BiFunction<Request, Response, String> f) throws Exception {
		executeFunction("ANY", path, f);
	}

	public void executeFunction(String method, String path, BiFunction<Request, Response, String> f) throws Exception {
		if (respondido && !method.equalsIgnoreCase("ANY"))
			return;
		if (!req.getMethod().equalsIgnoreCase(method) && !method.equalsIgnoreCase("ANY"))
			return;
		if (!path.equalsIgnoreCase(req.getPath().toString()))
			return;

		PrintStream body = new PrintStream(res.getPrintStream());
		body.println(f.apply(req, res));
		body.close();
		respondido = true;
	}

	public void notFound(BiFunction<Request, Response, String> f) throws Exception {
		if (respondido)
			return;
		PrintStream body = new PrintStream(res.getPrintStream());
		body.println(f.apply(req, res));
		body.close();
		respondido = true;
	}

}
