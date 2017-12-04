package com.api.service;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.AccountDao;
import com.api.dao.BonusDao;
import com.api.entitie.Account;
import com.api.entitie.Bonus;
import com.api.http.MyHttpRequest;
import com.api.utils.Utils;

@Path("/bonus")
public class BonusService {

	final static Logger logger = Logger.getLogger(BonusService.class.getName());
	
	@GET
	@Path("/boomcraft/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusBoomcraft() {
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		//JSONObject json = myHttpRequest.getJsonByHttp(URL_BONUS);
		JSONObject json = new JSONObject();
		try {
			
			
			json.put("boomcraft", randomBool());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@GET
	@Path("/farmvillage/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusFarmvillage() {
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		//JSONObject json = myHttpRequest.getJsonByHttp(URL_BONUS);
		JSONObject json = new JSONObject();
		try {
			json.put("farmvillage", randomBool());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@GET
	@Path("/howob/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusHowob(@PathParam("uid") String uuid) {
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		
		System.out.println("Ahttp://howob.masi-henallux.be/api/veggiecrush/amelioration/"+uuid);
		JSONObject json = myHttpRequest.getJsonByHttp("http://howob.masi-henallux.be/api/veggiecrush/amelioration/"+uuid);
		JSONObject jsonRetour = new JSONObject();
		int qte = 0;
		try {
			if(!json.isNull("qte")) {
				qte = json.getInt("qte");
			}
			System.out.println(qte);
			if (qte > 0) {
				jsonRetour.put("howob", true);
			} else {
				jsonRetour.put("howob", false);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}
	
	@POST
	@Path("/notifier")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response notifierBonus (String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		MyHttpRequest myHttpRequest = new MyHttpRequest();

		jsonEnvoi = Utils.parseJsonObject(value);
		String uuid = null;
		
		if (jsonEnvoi.containsKey("uuid") && jsonEnvoi.get("uuid") != null) {
			uuid = (String) jsonEnvoi.get("uuid");
		} else {
			return Response.status(500).entity("KO").build();
		}
		
		org.json.simple.JSONObject jsonRetour = new org.json.simple.JSONObject();
		Boolean tmp = false;
		if (jsonEnvoi.containsKey("howob") && jsonEnvoi.get("howob") != null) {
			tmp = (Boolean) jsonEnvoi.get("howob");
			if (tmp) {
				jsonRetour.put("qte", 1);
				myHttpRequest.getJsonByPostWithJsonBody("http://howob.masi-henallux.be/api/veggiecrush/amelioration/"+uuid, jsonRetour);
			}
		}
		
		jsonRetour = new org.json.simple.JSONObject();
		tmp = false;
		if (jsonEnvoi.containsKey("farmvillage") && jsonEnvoi.get("farmvillage") != null) {
			tmp = (Boolean) jsonEnvoi.get("farmvillage");

			if (tmp) {
				
				// On appelle le FARMVILLAGE URL
			}
		}
		
		jsonRetour = new org.json.simple.JSONObject();
		tmp = false;
		if (jsonEnvoi.containsKey("howob") && jsonEnvoi.get("howob") != null) {
			tmp = (Boolean) jsonEnvoi.get("howob");
			if (tmp) {
				// On appelle le FARMVILLAGE URL
			}
		}

		
		return Response.status(200).entity("ok").build();
	}
	
	
	
	public Boolean randomBool() {
		
		return (Math.random() >= 0.5);
	}
}
