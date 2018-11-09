package com.stocos.servidor;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public interface ServerListener {

	void onServerStart();

	void onServerStop();

	void onServerRequest(Request req);

	void onServerResponse(Request req, Response res);

	void onPortChange(int novaPorta);

}
