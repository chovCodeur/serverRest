package com.api.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;

public final class Utils {
	
	public static String SALT = "3iLh3nalluX";
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
	
	public static JSONObject getJsonError(String name, int code, String message) {
		JSONObject json = new JSONObject();
		JSONObject jsonTemp = new JSONObject();

		try {
			jsonTemp.put("message", message);
			jsonTemp.put("err", code);
			json.put(name, jsonTemp);
			
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
	
	
	/**
	 * Permet de crypter le mot de passe avec SHA-512 et la clef commune
	 * @param passwordToHash
	 * @return mot de passe
	 */
	public static String get_SHA_512_SecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(SALT.getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

}
