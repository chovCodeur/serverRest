package com.api.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Classe utils permettant de mettre à diposition des outils
 */
public final class Utils {

	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	/**
	 * On controle une string avant de la mettre dans un json
	 * 
	 * @param string
	 * @return <code>true</code> si conforme, <code>false</code> sinon
	 */
	public static Boolean testStringForJson(String string) {
		return (string != null && !string.equals(""));
	}

	/**
	 * Permet de convertir une string en JSON
	 * 
	 * @param string
	 * @return json
	 */
	public static org.json.simple.JSONObject parseJsonObject(String s) {
		org.json.simple.JSONObject json = new org.json.simple.JSONObject();
		try {
			json = (org.json.simple.JSONObject) new JSONParser().parse(s);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Permet de retourner un message d'erreur uniforme en fonction du message et du
	 * code passé
	 * 
	 * @param name
	 * @param code
	 * @param message
	 * @return json
	 */
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

	/**
	 * Permet de crypter le mot de passe avec SHA-512 et la clef commune
	 * 
	 * @param passwordToHash
	 * @return mot de passe
	 */
	public static String get_SHA_512_SecurePassword(String passwordToHash) {
		String generatedPassword = null;
		try {
			// instance de cryptage
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			// Grain de sel externalisé dans un fichier de configuration
			md.update(applicationProperties.getString("bd.pass.salt").getBytes("UTF-8"));
			byte[] bytes = md.digest(passwordToHash.getBytes("UTF-8"));

			// on construit le mot de passe
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
