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
import com.api.entitie.Account;
import com.api.others.MyHttpRequest;
import com.api.utils.Utils;

@Path("/test")
public class TestService {

	final static Logger logger = Logger.getLogger(AccountService.class.getName());
	final static String URL_1 = "https://slack.com/api/api.test";

	
	@GET
	@Path("/testHTTP")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHttp() {
		System.out.println("On est en GET sur testHttp");

			MyHttpRequest myHttpRequest = new MyHttpRequest();
			JSONObject json = new JSONObject();
		try {
			json = myHttpRequest.getJsonByHttp(URL_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
	
	
	@POST
	@Path("/testHTTP")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Post() {
		
		System.out.println("On est en POST sur testHttp");
			MyHttpRequest myHttpRequest = new MyHttpRequest();
			JSONObject json = new JSONObject();
		try {
			json = myHttpRequest.getJsonByHttp(URL_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
	
	@GET
	@Path("/testHTTPS")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHttps() {
			MyHttpRequest myHttpRequest = new MyHttpRequest();
			JSONObject json = new JSONObject();
		try {
			json = myHttpRequest.getJsonByHttp(URL_1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
	
	@POST
	@Path("/testHttpWithJSON")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHttpWithJSON(String value) {
			org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
			jsonEnvoi = Utils.parseJsonObject(value);			
			MyHttpRequest myHttpRequest = new MyHttpRequest();
			JSONObject jsonRetour = new JSONObject();
		try {
			jsonRetour = myHttpRequest.getJsonByPostWithJsonBody("http://howob.masi-henallux.be/api/auth/signin/", jsonEnvoi);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + jsonRetour.toString());
		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	
}
	