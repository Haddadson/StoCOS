package com.stocos.servidor;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public interface ServerListener {

	void onServerStart(Servidor servidor);

	void onServerStop(Servidor servidor);

	void onServerRequest(Request request);

	void onServerResponse(Response response, String data);

}