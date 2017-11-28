package com.api.utils;

import java.sql.Timestamp;
import java.text.ParseException;


import org.json.simple.JSONObject;
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
	
	public static JSONObject parseJsonObject (String s) {
		JSONObject json = new JSONObject();
        try {
			json = (JSONObject) new JSONParser().parse(s);
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return json;
	}

}
