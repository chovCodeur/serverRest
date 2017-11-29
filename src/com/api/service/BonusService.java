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
	final static String URL_BONUS = "https://slack.com/api/api.test";
	
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
	public Response bonusHowob() {
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		//JSONObject json = myHttpRequest.getJsonByHttp(URL_BONUS);
		JSONObject json = new JSONObject();
		try {
			json.put("howob", randomBool());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
	
	@POST
	@Path("/uuid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusByUuid (String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		
		BonusDao bonusDao = new BonusDao();
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		String uuid = new String();
		if (jsonEnvoi.containsKey("uuid") && jsonEnvoi.get("uuid") != null) {
			uuid = (String) jsonEnvoi.get("uuid");
			listeBonus = bonusDao.getBonusByIdAccount(uuid);
		}

		JSONObject json = new JSONObject();
		try {
			for (Bonus bonus : listeBonus) {
				json.put(String.valueOf(bonus.getId_objet()), bonus.getJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	/*	if (json.length() == 0 ) {
			try {
				json.put("error", Utils.getJsonErrorGenerale());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} */
		
		return Response.status(200).entity(json.toString()).build();
	}
	
	
	public Boolean randomBool() {
		
		return (Math.random() >= 0.5);
	}
}
