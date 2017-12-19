package com.api.http;

import java.util.ResourceBundle;

import org.codehaus.jettison.json.JSONObject;

public class Test {

    private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	public static void main(String[] args) {

		System.out.println("MiPa");
		
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		
		JSONObject jsonObjectHttp = new JSONObject();
		org.json.simple.JSONObject donneesObjectHttp = new org.json.simple.JSONObject();
		donneesObjectHttp.put("username", "howob");
		donneesObjectHttp.put("password", "howob");
		//jsonObjectHttp = myHttpRequest.getJsonByHttp(applicationProperties.getString("bonus.howob")+"9bef617a-0b54-4da2-86a0-e686717cc434");
		//jsonObjectHttp = myHttpRequest.getJsonByHttp("https://veggiecrush.masi-henallux.be/rest_server/api/bonus/farmvillage/9bef617a-0b54-4da2-86a0-e686717cc434");
		jsonObjectHttp = myHttpRequest.getJsonByPostWithJsonBody("https://howob.masi-henallux.be/api/auth/signin", donneesObjectHttp);

		System.out.println(jsonObjectHttp.toString());
		
		
		System.out.println("Result HTTP : ");
		JSONObject jsonObjectHttps = new JSONObject();
		org.json.simple.JSONObject donneesObjectHttps = new org.json.simple.JSONObject();
		donneesObjectHttps.put("username", "howob");
		donneesObjectHttps.put("password", "howob");

		System.out.println("\n \n \n Result HTTPS : ");
		//jsonObjectHttps = myHttpRequest.getJsonByHttps(applicationProperties.getString("bonus.howob")+"9bef617a-0b54-4da2-86a0-e686717cc434");
		//jsonObjectHttps = myHttpRequest.getJsonByHttps("https://veggiecrush.masi-henallux.be/rest_server/api/bonus/farmvillage/9bef617a-0b54-4da2-86a0-e686717cc434");
		jsonObjectHttps = myHttpRequest.getJsonByPostWithJsonBodyHttps("https://howob.masi-henallux.be/api/auth/signin", donneesObjectHttps);
	
		System.out.println(jsonObjectHttps.toString());
	}

}
