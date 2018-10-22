package com.stocos.json;

import org.json.JSONArray;
import org.json.JSONObject;

public interface JsonFormatter {
	
	public JSONObject toJson();
	
	// public Object fromJson(JSONObject json);

	public default JSONArray toJsonArray() {
		return new JSONArray().put(toJson());
	}
}