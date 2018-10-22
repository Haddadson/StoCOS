package com.stocos.servidor;

import org.simpleframework.http.Request;
import org.simpleframework.http.Response;

public interface ServerListener {

	void onServerStart();

	void onServerStop();

	void onServerRequest(Request request);
	
	void onServerResponse(Response response, String data);

}