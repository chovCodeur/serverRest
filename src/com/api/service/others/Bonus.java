package com.api.service.others;

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
import com.api.http.MyHttpRequest;
import com.api.service.AccountService;
import com.api.utils.Utils;

@Path("/bonus")
public class Bonus {

	final static Logger logger = Logger.getLogger(Bonus.class.getName());
	final static String URL_BONUS = "https://slack.com/api/api.test";
	
	@GET
	@Path("/boomcraft/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testHttp() {
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
	public Response Post() {
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
	public Response testHttps() {
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
	
	public Boolean randomBool() {
		
		return (Math.random() >= 0.5);
	}
}
