package com.api.others;

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

	public JSONObject getJsonByHttp (String urlHttp) throws IOException {
		URL url = new URL(urlHttp);
		JSONObject jObject = new JSONObject();
		
		try {
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			logger.info("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				jObject = new JSONObject(response.toString());
				logger.debug(" trouvé : " + jObject.toString());

			} else {
				logger.error("GET request not worked");
			}
		} catch (ConnectException e) {
			// e.printStackTrace();
			logger.error("Erreur de connexion");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
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
				System.out.println(" trouvé : " + jObject.toString());

			} else {
				System.out.println("GET request not worked");
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

	private void printCert (HttpsURLConnection con) {
		if (con != null) {
			try {
				System.out.println("Response Code : " + con.getResponseCode());
				System.out.println("Cipher Suite : " + con.getCipherSuite());
				System.out.println("\n");

				Certificate[] certs = con.getServerCertificates();
				for (Certificate cert : certs) {
					System.out.println("Cert Type : " + cert.getType());
					System.out.println("Cert Hash Code : " + cert.hashCode());
					System.out.println("Cert Public Key Algorithm : " + cert.getPublicKey().getAlgorithm());
					System.out.println("Cert Public Key Format : " + cert.getPublicKey().getFormat());
					System.out.println("\n");
				}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public JSONObject getJsonByPostWithJsonBody (String url, org.json.simple.JSONObject jsonObject) throws Exception {

		//String url = "http://howob.masi-henallux.be/api/auth/signin/";
		URL obj = new URL(url);
		System.out.println("MiPaAaa");
		JSONObject jsonObjectReponse = new JSONObject();
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("POST");
		//con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type","application/json");

		//JSONObject jsonObject = new JSONObject();
		//jsonObject.put("username", "test");
		//jsonObject.put("password", "test");

		
		con.setDoOutput(true);
		
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jsonObject.toString());
		wr.flush();
		wr.close();
		
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
			
		if (responseCode == HttpURLConnection.HTTP_OK) {
			System.out.println("nSending 'POST' request to URL : " + url);
			System.out.println("Post Data : " + jsonObject.toString());
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String output;
			StringBuffer response = new StringBuffer();

			while ((output = in.readLine()) != null) {
				response.append(output);
			}
			in.close();
			

			jsonObjectReponse = new JSONObject(response.toString());
			System.out.println(" trouvé : " + jsonObjectReponse.toString());

			//printing result from response
			System.out.println(response.toString());
		} else {

		}
		
		return jsonObjectReponse;
	}

}
