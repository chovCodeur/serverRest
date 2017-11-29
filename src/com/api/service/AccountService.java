package com.api.service;

import java.util.ArrayList;
import java.util.ResourceBundle;

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
import com.api.utils.Utils;

@Path("/account")
public class AccountService {

	private static ResourceBundle messageProperties = ResourceBundle.getBundle("message");

	final static Logger logger = Logger.getLogger(AccountService.class.getName());

	/*
	 * @GET
	 * 
	 * @Path("/getAll")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response getAllUsers() {
	 * AccountDao accountDao = new AccountDao();
	 * 
	 * ArrayList<Account> accounts = new ArrayList<Account>(); accounts =
	 * accountDao.getAllAccounts(); JSONObject json = new JSONObject(); try { for
	 * (Account account : accounts) { json.put(String.valueOf(account.getId()),
	 * account.getJson()); } } catch (JSONException e) { e.printStackTrace(); }
	 * return Response.status(200).entity(json.toString()).build(); }
	 */

	@GET
	@Path("/username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByUsername(@PathParam("username") String username) {
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountByUsername(username);
		JSONObject json = new JSONObject();
		if (account != null) {
			json = account.getJson();
		} else {
			try {
				json.put("error", messageProperties.getString("api.error.compte.aucun"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@GET
	@Path("/uid/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByUid(@PathParam("uid") String uid) {
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccounByUid(uid);
		JSONObject json = new JSONObject();
		if (account != null) {
			json = account.getJson();
		} else {
			try {
				json.put("error", messageProperties.getString("api.error.compte.aucun"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") String id) {
		System.out.println("____MiPA______" + id);
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountById(Integer.valueOf(id));
		JSONObject json = new JSONObject();
		if (account != null) {
			json = account.getJson();
		} else {
			try {
				json.put("error", messageProperties.getString("api.error.compte.aucun"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@POST
	@Path("/signin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signinAccount(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		AccountDao accountDao = new AccountDao();
		Account account = new Account();
		
		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			account = accountDao.getAccountByUsername(username);
		}

		String password = new String();
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
		}


		JSONObject jsonRetour = new JSONObject();
		if (account != null && account.getId() != 0) {
			try {
				if (account.getPassword().equals(password)) {
					jsonRetour.put("user", account.getJsonForApi());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if (jsonRetour.length() == 0 ) {
			try {
				jsonRetour.put("error", Utils.getJsonError());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}
	
	@POST
	@Path("/exist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testExistAccount(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		AccountDao accountDao = new AccountDao();
		Account account = new Account();
		Boolean mailExiste = false;
		Boolean usernameExiste = false;

		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			account = accountDao.getAccountByUsername(username);
			if (account != null && account.getId() != 0) {
				usernameExiste = true;
			}
		}


		String mail = new String();
		if (jsonEnvoi.containsKey("email") && jsonEnvoi.get("email") != null) {
			mail = (String) jsonEnvoi.get("email");
			account = accountDao.getAccountByMail(mail);
			if (account != null && account.getId() != 0) {
				mailExiste = true;
			}
		}


		JSONObject jsonRetour = new JSONObject();
		
		try {
			jsonRetour.put("mailExiste", mailExiste);
			jsonRetour.put("usernameExiste", usernameExiste);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		if (jsonRetour.length() == 0 ) {
			try {
				jsonRetour.put("error", Utils.getJsonErrorGenerale());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}
}
