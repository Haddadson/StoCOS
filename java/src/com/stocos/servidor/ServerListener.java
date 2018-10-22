package com.stocos.servidor;

import org.simpleframework.http.Request;

public interface ServerListener {

	void onServerStart();

	void onServerStop();

	void onServerRequest(Request request);

}