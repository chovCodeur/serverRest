package com.api.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
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



@Path("/account")
public class AccountService {

	final static Logger logger = Logger.getLogger(AccountService.class.getName());

	@GET
	@Path("/getAllAccounts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		logger.info("Appel TEST_DEV getUser");
		AccountDao accountDao = new AccountDao();
		
		ArrayList<Account> accounts = new ArrayList<Account>();
		logger.debug("MiPa avant appel DAO");
		accounts = accountDao.getAllAccounts();	
		logger.info("Création du JSON ");
		
		JSONObject json = new JSONObject();
		try {
			for (Account account : accounts) {
				json.put(String.valueOf(account.getId()), account.getJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
	

	@GET
	@Path("/getAccountByUsername/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByUsername(@PathParam("username") String username) {
		AccountDao accountDao = new AccountDao();
		
		
		Account account = accountDao.getAccountByUsername(username);
		JSONObject json = new JSONObject();
		json = account.getJson();
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
	
	@GET
	@Path("/getAccountByUid/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByUid(@PathParam("uid") String uid) {
		AccountDao accountDao = new AccountDao();
		
		Account account = accountDao.getAccounByUid(uid);
		JSONObject json = new JSONObject();
		json = account.getJson();
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
}
