package com.api.service;

import java.sql.Timestamp;
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
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.AccountDao;
import com.api.entitie.Account;
import com.api.http.MyHttpRequest;
import com.api.utils.Utils;
import com.sun.jersey.json.impl.provider.entity.JSONListElementProvider;

@Path("/account")
public class AccountService {

	final static Logger logger = Logger.getLogger(AccountService.class.getName());
    private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
/*
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsers() {
		AccountDao accountDao = new AccountDao();

		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = accountDao.getAllAccounts();
		JSONObject json = new JSONObject();
		try {
			for (Account account : accounts) {
				json.put(String.valueOf(account.getId()), account.getJsonForApi());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json.length() == 0) {
			json = Utils.getJsonError("error", 401, "Aucun compte trouvé");
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@GET
	@Path("/username/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByUsername(@PathParam("username") String username) {
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountByUsername(username);
		JSONObject json = new JSONObject();
		if (account != null) {
			json = account.getJsonForApi();
		} else {
			json = Utils.getJsonError("Error", 401, "Aucun compte trouve");
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
			json = account.getJsonForApi();
		} else {
			json = Utils.getJsonError("Error", 401, "Aucun compte trouve");
		}
		return Response.status(200).entity(json.toString()).build();
	}

	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") String id) {
		AccountDao accountDao = new AccountDao();
		Account account = accountDao.getAccountById(Integer.valueOf(id));
		JSONObject json = new JSONObject();
		if (account != null) {
			json = account.getJsonForApi();
		} else {
			json = Utils.getJsonError("Error", 401, "Aucun compte trouve");
		}
		return Response.status(200).entity(json.toString()).build();
	}
	*/

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
		} else {
			return Response.status(200)
					.entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul")).toString())
					.build();
		}

		String password = new String();
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
		} else {
			return Response.status(200)
					.entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul")).toString())
					.build();
		}

		JSONObject jsonRetour = new JSONObject();
		if (account != null && account.getId() != 0) {
			try {
				if (account.getPassword().equals(Utils.get_SHA_512_SecurePassword(password))) {
					jsonRetour.put("user", account.getJsonForApi());
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (jsonRetour.length() == 0) {
			return Response.status(200).entity(Utils.getJsonError("error", 401, applicationProperties.getString("message.compte.inexistant")).toString())
					.build();
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	@POST
	@Path("/existing")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response existingAccount(String value) {
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
		} else {
			return Response.status(200)
					.entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul")).toString())
					.build();
		}

		String mail = new String();
		if (jsonEnvoi.containsKey("email") && jsonEnvoi.get("email") != null) {
			mail = (String) jsonEnvoi.get("email");
			account = accountDao.getAccountByMail(mail);
			if (account != null && account.getId() != 0) {
				mailExiste = true;
			}
		} else {
			return Response.status(200)
					.entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.email.nul")).toString())
					.build();
		}

		JSONObject jsonRetour = new JSONObject();

		try {
			jsonRetour.put("email", mailExiste);
			jsonRetour.put("username", usernameExiste);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	@POST
	@Path("/signinAutreJeu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signinAutreJeu(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			if (username == null || username.equals("")) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul")).toString())
						.build();
			}
		} else {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul")).toString())
					.build();

		}

		String password = new String();
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
			if (password == null || password.equals("")) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul")).toString())
						.build();
			}
		} else {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul")).toString())
					.build();
		}

		JSONObject jsonRetour = new JSONObject();


		jsonRetour = appelerSignInAutreJeu(applicationProperties.getString("signin.howob"), jsonEnvoi, false);
		if (jsonRetour == null) {
			jsonRetour = appelerSignInAutreJeu(applicationProperties.getString("signin.farmvillage"), jsonEnvoi, false);
			if (jsonRetour == null) {
				jsonRetour = appelerSignInAutreJeu(applicationProperties.getString("signin.boomcraft"), jsonEnvoi, false);
			}
		}
		
		if (jsonRetour == null) {
			try {
				jsonRetour = new JSONObject();
				jsonRetour.put("signin", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	@POST
	@Path("/existingAutreJeu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response existingAutreJeu(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			if (username == null || username.equals("")) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul")).toString())
						.build();
			}
		} else {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul")).toString())
					.build();

		}

		String email = new String();
		if (jsonEnvoi.containsKey("email") && jsonEnvoi.get("email") != null) {
			email = (String) jsonEnvoi.get("email");
			if (email == null || email.equals("")) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.email.nul")).toString())
						.build();
			}
		} else {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.email.nul")).toString())
					.build();
		}

		JSONObject jsonRetour = new JSONObject();
		if (appelerExistingAutreJeu(applicationProperties.getString("existing.howob"), jsonEnvoi, false)) {
			try {
				jsonRetour.put("existing", "howob");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if (appelerExistingAutreJeu(applicationProperties.getString("existing.farmvillage"), jsonEnvoi, false)) {
			try {
				jsonRetour.put("existing", "famvillage");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (appelerExistingAutreJeu(applicationProperties.getString("existing.boomcraft"), jsonEnvoi, false)) {
			try {
				jsonRetour.put("existing", "boomcraft");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		if (jsonRetour.length() == 0) {
			try {
				jsonRetour.put("existing", "null");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}
	


	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertNewAccount(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		String id_global = (String) jsonEnvoi.get("id_global");
		String username = (String) jsonEnvoi.get("username");
		String email = (String) jsonEnvoi.get("email");
		String password = (String) jsonEnvoi.get("password");

		String passwordHash = Utils.get_SHA_512_SecurePassword(password);
		String faction = (String) jsonEnvoi.get("faction");
		Timestamp created_at = (Timestamp) jsonEnvoi.get("created_at");
		Timestamp updated_at = (Timestamp) jsonEnvoi.get("updated_at");
		Timestamp deleted_at = (Timestamp) jsonEnvoi.get("deleted_at");

		Account account = new Account(0, id_global, username, email, passwordHash, faction, created_at, updated_at,
				deleted_at);

		AccountDao accountDao = new AccountDao();
		Boolean insertion = accountDao.insertNewAccount(account);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("error_insert", insertion);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	
	@POST
	@Path("/updatePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePassword(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		int id = 0;
		if (jsonEnvoi.containsKey("id")) {
			id = Integer.valueOf((String) jsonEnvoi.get("id"));
			if (id == 0) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul")).toString())
						.build();
			}
		} else {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul")).toString())
					.build();

		}

		String password;
		String passwordHash;
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
			if (password == null || password.equals("")) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul")).toString())
						.build();
			} else {
				passwordHash = Utils.get_SHA_512_SecurePassword(password);
			}
		} else {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul")).toString())
					.build();
		}

		AccountDao accountDao = new AccountDao();
		Boolean insertion = accountDao.updatePasswordById(id, passwordHash);

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("error_update", insertion);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonObject.toString()).build();
	}


	private JSONObject appelerSignInAutreJeu(String url, org.json.simple.JSONObject jsonEnvoi, Boolean withD) {
		JSONObject jsonRetourTemporaire = new JSONObject();
		MyHttpRequest httpRequest = new MyHttpRequest();
		jsonRetourTemporaire = httpRequest.getJsonByPostWithJsonBody(url, jsonEnvoi);

		JSONObject jsonRetour = new JSONObject();

		if (withD) {
			try {
				if (jsonRetourTemporaire.isNull("d") || jsonRetourTemporaire.getJSONObject("d").isNull("user")) {
					return null;
				} else {
					jsonRetour.put("signin", true);
					if (!jsonRetourTemporaire.getJSONObject("d").getJSONObject("user").isNull("globalId")) {
						jsonRetour.put("uuid_ailleurs", jsonRetourTemporaire.getJSONObject("d").getJSONObject("user").getString("globalId"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}	
		} else {
			try {
				if (jsonRetourTemporaire.isNull("user")) {
					return null;
				} else {
					jsonRetour.put("signin", true);
					if (!jsonRetourTemporaire.getJSONObject("user").isNull("globalId")) {
						jsonRetour.put("uuid_ailleurs", jsonRetourTemporaire.getJSONObject("user").get("globalId"));
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jsonRetour;
	}
	
	private Boolean appelerExistingAutreJeu(String url, org.json.simple.JSONObject jsonEnvoi, Boolean withD) {
		JSONObject jsonRetourIntermediaire = new JSONObject();
		MyHttpRequest httpRequest = new MyHttpRequest();

		jsonRetourIntermediaire = httpRequest.getJsonByPostWithJsonBody(url, jsonEnvoi);
		if (withD) {
			if (!jsonRetourIntermediaire.isNull("d")) {
				try {
					if (!jsonRetourIntermediaire.getJSONObject("d").isNull("email")) {
						Boolean mailExist = (Boolean) jsonRetourIntermediaire.getJSONObject("d").get("email");
						if (mailExist) {
							return true;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	
			if (!jsonRetourIntermediaire.isNull("d")) {
				try {
					if (!jsonRetourIntermediaire.getJSONObject("d").isNull("username")) {
						Boolean usernameExist = (Boolean) jsonRetourIntermediaire.getJSONObject("d").get("username");
						if (usernameExist) {
							return true;
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			
			if (!jsonRetourIntermediaire.isNull("email")) {
				try {
					Boolean mailExist = (Boolean) jsonRetourIntermediaire.get("email");
					if (mailExist) {
						return true;
					}
	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	
			if (!jsonRetourIntermediaire.isNull("username")) {
				try {
					Boolean usernameExist = (Boolean) jsonRetourIntermediaire.get("username");
					if (usernameExist) {
						return true;
					}
	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}

		return false;
	}
}
