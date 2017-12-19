package com.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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

/**
 * Classe permettant d'exposer les services en rapport avec la table ACCOUNT
 */
@Path("/account")
public class AccountService {

	final static Logger logger = Logger.getLogger(AccountService.class.getName());
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	/**
	 * Permettant de se connecter avec un compte
	 * 
	 * @param json
	 * @return json
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

		// controle du champ username
		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			account = accountDao.getAccountByUsername(username);
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul"))
							.toString())
					.build();
		}

		// controle du champ password
		String password = new String();
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul"))
							.toString())
					.build();
		}

		// recherche en BD
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

		// si compte inexistant, on renvoi un erreur
		if (jsonRetour.length() == 0) {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 401, applicationProperties.getString("message.compte.inexistant"))
							.toString())
					.build();
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	/**
	 * Permet de verifier si un compte existe chez nous (username et mail)
	 * 
	 * @param json
	 * @return json
	 */
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

		// controle du champ username
		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			account = accountDao.getAccountByUsername(username);
			if (account != null && account.getId() != 0) {
				usernameExiste = true;
			}
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul"))
							.toString())
					.build();
		}

		// controle du champ email
		String mail = new String();
		if (jsonEnvoi.containsKey("email") && jsonEnvoi.get("email") != null) {
			mail = (String) jsonEnvoi.get("email");
			account = accountDao.getAccountByMail(mail);
			if (account != null && account.getId() != 0) {
				mailExiste = true;
			}
		} else {
			return Response.status(200).entity(Utils
					.getJsonError("error", 500, applicationProperties.getString("message.erreur.email.nul")).toString())
					.build();
		}

		JSONObject jsonRetour = new JSONObject();

		// json de retour
		try {
			jsonRetour.put("email", mailExiste);
			jsonRetour.put("username", usernameExiste);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	/**
	 * Permet de voir depuis notre application si l'on peut se connecter sur un
	 * autre API
	 * 
	 * @param header
	 * @param json
	 * @return json
	 */
	@POST
	@Path("/signinAutreJeu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response signinAutreJeu(@Context HttpHeaders header, String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		List<String> authHeaders = header.getRequestHeader("Authorization");
		// controle du header
		if (authHeaders != null && authHeaders.get(0) != null) {
			if (!authHeaders.get(0).equals(applicationProperties.getString("api.token.secure.veggie.client"))) {
				return Response.status(200).entity(Utils
						.getJsonError("error", 500, applicationProperties.getString("message.erreur.mauvais.token"))
						.toString()).build();
			}
		} else {
			return Response.status(200).entity(Utils
					.getJsonError("error", 500, applicationProperties.getString("message.erreur.no.token")).toString())
					.build();

		}
		// controle du champ username
		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			if (username == null || username.equals("")) {
				return Response.status(200).entity(
						Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul"))
								.toString())
						.build();
			}
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul"))
							.toString())
					.build();

		}
		// controle du champ password
		String password = new String();
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
			if (password == null || password.equals("")) {
				return Response.status(200).entity(
						Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul"))
								.toString())
						.build();
			}
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul"))
							.toString())
					.build();
		}

		JSONObject jsonRetour = new JSONObject();

		// on appel en cascade les autres API
		jsonRetour = appelerSignInAutreJeu(applicationProperties.getString("signin.howob"), jsonEnvoi);
		if (jsonRetour == null) {
			jsonRetour = appelerSignInAutreJeu(applicationProperties.getString("signin.farmvillage"), jsonEnvoi);
			if (jsonRetour == null) {
				jsonRetour = appelerSignInAutreJeu(applicationProperties.getString("signin.boomcraft"), jsonEnvoi);
			}
		}

		// si aucune réponse, on renvoi false
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

	/**
	 * Permet de vérifier qu'un compte existe dans une autre jeu (username et email)
	 * 
	 * @param header
	 * @param json
	 * @return json
	 */
	@POST
	@Path("/existingAutreJeu")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response existingAutreJeu(@Context HttpHeaders header, String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		List<String> authHeaders = header.getRequestHeader("Authorization");
		// controle du header
		if (authHeaders != null && authHeaders.get(0) != null) {
			if (!authHeaders.get(0).equals(applicationProperties.getString("api.token.secure.veggie.client"))) {
				return Response.status(200).entity(Utils
						.getJsonError("error", 500, applicationProperties.getString("message.erreur.mauvais.token"))
						.toString()).build();
			}
		} else {
			return Response.status(200).entity(Utils
					.getJsonError("error", 500, applicationProperties.getString("message.erreur.no.token")).toString())
					.build();
		}

		// controle du champ username
		String username = new String();
		if (jsonEnvoi.containsKey("username") && jsonEnvoi.get("username") != null) {
			username = (String) jsonEnvoi.get("username");
			if (username == null || username.equals("")) {
				return Response.status(200).entity(
						Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul"))
								.toString())
						.build();
			}
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.username.nul"))
							.toString())
					.build();

		}

		// controle du champ email
		String email = new String();
		if (jsonEnvoi.containsKey("email") && jsonEnvoi.get("email") != null) {
			email = (String) jsonEnvoi.get("email");
			if (email == null || email.equals("")) {
				return Response.status(200)
						.entity(Utils
								.getJsonError("error", 500, applicationProperties.getString("message.erreur.email.nul"))
								.toString())
						.build();
			}
		} else {
			return Response.status(200).entity(Utils
					.getJsonError("error", 500, applicationProperties.getString("message.erreur.email.nul")).toString())
					.build();
		}

		// Appel de l'api howob
		JSONObject jsonRetour = new JSONObject();
		if (appelerExistingAutreJeu(applicationProperties.getString("existing.howob"), jsonEnvoi)) {
			try {
				jsonRetour.put("existing", "howob");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// appel de l'api farmvillage
		if (appelerExistingAutreJeu(applicationProperties.getString("existing.farmvillage"), jsonEnvoi)) {
			try {
				jsonRetour.put("existing", "famvillage");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// appel de l'api boomcraft
		if (appelerExistingAutreJeu(applicationProperties.getString("existing.boomcraft"), jsonEnvoi)) {
			try {
				jsonRetour.put("existing", "boomcraft");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		// si aucune réponse, on renvoi null
		if (jsonRetour.length() == 0) {
			try {
				jsonRetour.put("existing", "null");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	/**
	 * Permet d'insérer une nouvelle ligne dans la table ACCOUNT
	 * 
	 * @param header
	 * @param json
	 * @return json
	 */
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertNewAccount(@Context HttpHeaders header, String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		List<String> authHeaders = header.getRequestHeader("Authorization");
		// controle du header
		if (authHeaders != null && authHeaders.get(0) != null) {
			if (!authHeaders.get(0).equals(applicationProperties.getString("api.token.secure.veggie.client"))) {
				return Response.status(200).entity(Utils
						.getJsonError("error", 500, applicationProperties.getString("message.erreur.mauvais.token"))
						.toString()).build();
			}
		} else {
			return Response.status(200).entity(Utils
					.getJsonError("error", 500, applicationProperties.getString("message.erreur.no.token")).toString())
					.build();
		}

		// récupération des données
		String id_global = (String) jsonEnvoi.get("id_global");
		String username = (String) jsonEnvoi.get("username");
		String email = (String) jsonEnvoi.get("email");
		String password = (String) jsonEnvoi.get("password");

		String passwordHash = Utils.get_SHA_512_SecurePassword(password);
		String faction = (String) jsonEnvoi.get("faction");
		Timestamp created_at = (Timestamp) jsonEnvoi.get("created_at");
		Timestamp updated_at = (Timestamp) jsonEnvoi.get("updated_at");
		Timestamp deleted_at = (Timestamp) jsonEnvoi.get("deleted_at");

		// insertion
		Account account = new Account(0, id_global, username, email, passwordHash, faction, created_at, updated_at,
				deleted_at);

		AccountDao accountDao = new AccountDao();
		Boolean insertion = accountDao.insertNewAccount(account);

		JSONObject jsonObject = new JSONObject();
		// si pb à l'insertion, on le renvoie à l'API
		try {
			jsonObject.put("error_insert", insertion);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonObject.toString()).build();
	}

	/**
	 * Permet de mettre à jour un mot de passe dans la table ACCOUNT
	 * 
	 * @param header
	 * @param json
	 * @return json
	 */
	@POST
	@Path("/updatePassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePassword(@Context HttpHeaders header, String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);

		List<String> authHeaders = header.getRequestHeader("Authorization");
		// controle du header
		if (authHeaders != null && authHeaders.get(0) != null) {
			if (!authHeaders.get(0).equals(applicationProperties.getString("api.token.secure.veggie.client"))) {
				return Response.status(200).entity(Utils
						.getJsonError("error", 500, applicationProperties.getString("message.erreur.mauvais.token"))
						.toString()).build();
			}
		} else {
			return Response.status(200).entity(Utils
					.getJsonError("error", 500, applicationProperties.getString("message.erreur.no.token")).toString())
					.build();
		}

		// controle du champ id
		int id = 0;
		if (jsonEnvoi.containsKey("id")) {
			id = Integer.valueOf((String) jsonEnvoi.get("id"));
			if (id == 0) {
				return Response.status(200).entity(Utils
						.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul"))
						.toString()).build();
			}
		} else {
			return Response.status(200).entity(
					Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul"))
							.toString())
					.build();

		}

		// controle du champ password
		String password;
		String passwordHash;
		if (jsonEnvoi.containsKey("password") && jsonEnvoi.get("password") != null) {
			password = (String) jsonEnvoi.get("password");
			if (password == null || password.equals("")) {
				return Response.status(200).entity(
						Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul"))
								.toString())
						.build();
			} else {
				passwordHash = Utils.get_SHA_512_SecurePassword(password);
			}
		} else {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500, applicationProperties.getString("message.erreur.password.nul"))
							.toString())
					.build();
		}

		// modification en base
		AccountDao accountDao = new AccountDao();
		Boolean insertion = accountDao.updatePasswordById(id, passwordHash);

		// on retourne false s'il y a eu une erreur à l'insertion
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("error_update", !insertion);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonObject.toString()).build();
	}

	/**
	 * Permet d'appeler une api en fonction de l'adresse en paramètre
	 * 
	 * @param url
	 * @param jsonEnvoi
	 * @return json
	 */
	private JSONObject appelerSignInAutreJeu(String url, org.json.simple.JSONObject jsonEnvoi) {
		JSONObject jsonRetourTemporaire = new JSONObject();
		MyHttpRequest httpRequest = new MyHttpRequest();
		jsonRetourTemporaire = httpRequest.getJsonByPostWithJsonBody(url, jsonEnvoi);

		JSONObject jsonRetour = new JSONObject();

		try {
			// controle si l'api retourne un json avec "user"
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
		return jsonRetour;
	}

	/**
	 * Permet d'appeler les service de verification d'existance pour les autres API
	 * 
	 * @param url
	 * @param jsonEnvoi
	 * @return json
	 */
	private Boolean appelerExistingAutreJeu(String url, org.json.simple.JSONObject jsonEnvoi) {
		JSONObject jsonRetourIntermediaire = new JSONObject();
		MyHttpRequest httpRequest = new MyHttpRequest();

		jsonRetourIntermediaire = httpRequest.getJsonByPostWithJsonBody(url, jsonEnvoi);

		// controle de l'existance du champ email
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

		// controle de l'existance du champ username
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
		return false;
	}
}
