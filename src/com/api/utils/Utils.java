package com.api.utils;

import java.sql.Timestamp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;

public final class Utils {
	
	public static Boolean testStringForJson (String string) {
		return (string!= null && !string.equals(""));
	}
	
	public static Boolean testDateNulleForTimstamp (Timestamp timestamp) {
		Timestamp reference = new Timestamp(0);
		if (reference.equals(timestamp)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static org.json.simple.JSONObject parseJsonObject (String s) {
		org.json.simple.JSONObject json = new org.json.simple.JSONObject();
        try {
			json = (org.json.simple.JSONObject) new JSONParser().parse(s);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject getJsonError() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", "AuthError");
			json.put("message", "Erreur d'authentification");
			json.put("err", 401);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject getJsonErrorGenerale() {
		JSONObject json = new JSONObject();
		try {
			json.put("name", "Error");
			json.put("message", "Erreur interne de l'API VeggieCrush");
			json.put("err", 500);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

}
