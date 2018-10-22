package com.stocos.servidor;

import org.simpleframework.http.Request;

public interface ServerListener {
	void onRequest(Request request);
}