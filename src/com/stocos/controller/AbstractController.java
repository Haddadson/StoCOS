package com.stocos.controller;

import java.io.PrintStream;

import org.json.JSONObject;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public abstract class AbstractController implements IController {

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

	public void get(String path, Rota f) {
		executeFunction("GET", path, f);
	}

	public void post(String path, Rota f) {
		executeFunction("POST", path, f);
	}

	public void delete(String path, Rota f) {
		executeFunction("DELETE", path, f);
	}

	public void put(String path, Rota f) {
		executeFunction("PUT", path, f);
	}

	public void any(String path, Rota f) {
		executeFunction("ANY", path, f);
	}

	public void executeFunction(String method, String path, Rota r) {
		if (respondido && !method.equalsIgnoreCase("ANY"))
			return;
		if (!req.getMethod().equalsIgnoreCase(method) && !method.equalsIgnoreCase("ANY"))
			return;
		if (!path.equalsIgnoreCase(req.getPath().toString()))
			return;

		try {
			PrintStream body = new PrintStream(res.getPrintStream());
			String conteudo = "";
			try {
				conteudo = r.execute(req, res);
			} catch (Exception e) {
				conteudo = new JSONObject().put("Erro", e.getMessage()).toString();
			}
			body.println(conteudo);
			body.close();
			respondido = true;
		} catch (Exception e) {
			System.err.println("Erro ao obter Stream da Response: " + e.getMessage());
		}
	}
}
