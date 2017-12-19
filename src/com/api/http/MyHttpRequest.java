package com.api.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class MyHttpRequest {
	
	public MyHttpRequest() {
		
	}
	private static final Logger logger = Logger.getLogger(MyHttpRequest.class.getName());
	
	public JSONObject getJsonByHttp (String urlHttp) {
		
		JSONObject jObject = new JSONObject();
		
		try {
			URL url = new URL(urlHttp);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
			System.out.println("Retour"+responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jObject = new JSONObject(response.toString());

			} else {
				
			}
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jObject;
	}
	
	public JSONObject getJsonByHttpWithToken (String urlHttp, String token) {
		
		JSONObject jObject = new JSONObject();
		
		try {
			URL url = new URL(urlHttp);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty ("Authorization-BFHV", token);

			
			int responseCode = con.getResponseCode();
			System.out.println("Retour"+responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jObject = new JSONObject(response.toString());

			} else {
				
			}
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jObject;
	}

	
	public JSONObject getJsonByHttps(String urlHttps) {

		JSONObject jObject = new JSONObject();

		try {
			URL url = new URL(urlHttps);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				jObject = new JSONObject(response.toString());

			} else {
				
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jObject;

	}

	public JSONObject getJsonByPostWithJsonBody (String url, org.json.simple.JSONObject jsonObject) {
		
		JSONObject jsonObjectReponse = new JSONObject();

		try {
			URL obj = new URL(url);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			//HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type","application/json");

			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonObject.toString());
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();

				while ((output = in.readLine()) != null) {
					response.append(output);
				}
				in.close();
				
				jsonObjectReponse = new JSONObject(response.toString());
			} else {

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObjectReponse;
	}
	
	public JSONObject getJsonByPostWithJsonBodyHttps (String url, org.json.simple.JSONObject jsonObject) {
		
		JSONObject jsonObjectReponse = new JSONObject();

		try {
			URL obj = new URL(url);
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type","application/json");

			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonObject.toString());
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();

				while ((output = in.readLine()) != null) {
					response.append(output);
				}
				in.close();
				
				jsonObjectReponse = new JSONObject(response.toString());
			} else {

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObjectReponse;
	}
	
	
	public JSONObject getJsonByPostWithJsonBodyHttpsAndToken (String url, org.json.simple.JSONObject jsonObject, String token) {
		
		JSONObject jsonObjectReponse = new JSONObject();

		try {
			URL obj = new URL(url);
			
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Content-Type","application/json");
			con.setRequestProperty ("Authorization-BFHV", token);

			con.setDoOutput(true);
			
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonObject.toString());
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String output;
				StringBuffer response = new StringBuffer();

				while ((output = in.readLine()) != null) {
					response.append(output);
				}
				in.close();
				
				jsonObjectReponse = new JSONObject(response.toString());
			} else {

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObjectReponse;
	}

}