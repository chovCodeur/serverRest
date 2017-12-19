package com.api.service;

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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.http.MyHttpRequest;
import com.api.utils.Utils;

/**
 * Classe permettant de consulter et de consommer les bonus disponibles dans les
 * autres jeux.
 */
@Path("/bonus")
public class BonusService {

	final static Logger logger = Logger.getLogger(BonusService.class.getName());
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	/**
	 * Permet de consulter les bonus présents dans le jeu Boomcraft
	 * 
	 * @param header
	 * @param uuid
	 * @return json
	 */
	@GET
	@Path("/boomcraft/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusBoomcraft(@Context HttpHeaders header, @PathParam("uid") String uuid) {

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

		// on interroge l'autre API
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		JSONObject json = myHttpRequest
				.getJsonByHttp(applicationProperties.getString("bonus.boomcraft") + "\"" + uuid + "\"");

		JSONObject jsonRetour = new JSONObject();
		int qte = 0;
		// si au moins une quantité, on retourne true, false sinon
		try {
			if (!json.isNull("bonus") && !json.getJSONObject("bonus").isNull("quantite")) {
				qte = Integer.valueOf((String) json.getJSONObject("bonus").get("quantite"));
			}
			if (qte > 0) {
				jsonRetour.put("boomcraft", true);
			} else {
				jsonRetour.put("boomcraft", false);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		try {
			jsonRetour.put("boomcraft", false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	/**
	 * Permet de consulter les bonus présents dans le jeu Farmvillage
	 * 
	 * @param header
	 * @param uuid
	 * @return json
	 */
	@GET
	@Path("/farmvillage/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusFarmvillage(@Context HttpHeaders header, @PathParam("uid") String uuid) {

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

		// on interroge l'autre API
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		JSONObject json = myHttpRequest.getJsonByHttp(applicationProperties.getString("bonus.farmvillage") + uuid);
		JSONObject jsonRetour = new JSONObject();

		// si au moins une quantité, on retourne true, false sinon
		int qte = 0;
		try {
			if (!json.isNull("bonus") && !json.getJSONObject("bonus").isNull("quantite")) {
				qte = Integer.valueOf((String) json.getJSONObject("bonus").get("quantite"));
			}
			if (qte > 0) {
				jsonRetour.put("farmvillage", true);
			} else {
				jsonRetour.put("farmvillage", false);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(jsonRetour.toString()).build();
	}

	/**
	 * Permet de consulter les bonus présents dans le jeu Howob
	 * 
	 * @param header
	 * @param uuid
	 * @return json
	 */
	@GET
	@Path("/howob/{uid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bonusHowob(@Context HttpHeaders header, @PathParam("uid") String uuid) {

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

		// on interroge l'autre API
		MyHttpRequest myHttpRequest = new MyHttpRequest();
		JSONObject json = myHttpRequest.getJsonByHttpWithToken(applicationProperties.getString("bonus.howob") + uuid,
				applicationProperties.getString("bonus.wobob.veggie.token"));

		JSONObject jsonRetour = new JSONObject();
		int qte = 0;
		// si au moins une quantité, on retourne true, false sinon
		try {
			if (!json.isNull("qte")) {
				qte = json.getInt("qte");
			}

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

	/**
	 * Permet de notifier les bonus utilisés par le joueur dans le jeu VeggieCrush
	 * 
	 * @param header
	 * @param uuid
	 * @return json
	 */
	@POST
	@Path("/notifier")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response notifierBonus(@Context HttpHeaders header, String value) {

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

		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		MyHttpRequest myHttpRequest = new MyHttpRequest();

		jsonEnvoi = Utils.parseJsonObject(value);
		String uuid = null;

		// controle du champ uuid
		if (jsonEnvoi.containsKey("uuid") && jsonEnvoi.get("uuid") != null) {
			uuid = (String) jsonEnvoi.get("uuid");
		} else {

			return Response.status(200).entity(Utils.getJsonError("error", 500, "L'uuid ne peut pas être vide"))
					.build();
		}

		// on notifie le jeu howob si l'on a consommé un bonus ou non
		org.json.simple.JSONObject jsonRetour = new org.json.simple.JSONObject();
		Boolean tmp = false;
		if (jsonEnvoi.containsKey("howob") && jsonEnvoi.get("howob") != null) {
			tmp = (Boolean) jsonEnvoi.get("howob");
			if (tmp) {
				jsonRetour.put("qte", 1);
				myHttpRequest.getJsonByPostWithJsonBodyHttpsAndToken(
						applicationProperties.getString("notifier.bonus.howob") + uuid, jsonRetour,
						applicationProperties.getString("bonus.wobob.veggie.token"));
			}
		}

		// on notifie le jeu farmvillage si l'on a consommé un bonus ou non
		jsonRetour = new org.json.simple.JSONObject();
		tmp = false;
		if (jsonEnvoi.containsKey("farmvillage") && jsonEnvoi.get("farmvillage") != null) {
			tmp = (Boolean) jsonEnvoi.get("farmvillage");
			if (tmp) {
				jsonRetour.put("qte", 1);
				myHttpRequest.getJsonByPostWithJsonBody(
						applicationProperties.getString("notifier.bonus.farmvillage") + uuid, jsonRetour);
			}
		}

		// on notifie le jeu boomcraft si l'on a consommé un bonus ou non
		jsonRetour = new org.json.simple.JSONObject();
		tmp = false;
		if (jsonEnvoi.containsKey("boomcraft") && jsonEnvoi.get("boomcraft") != null) {
			tmp = (Boolean) jsonEnvoi.get("boomcraft");
			if (tmp) {
				jsonRetour.put("qte", 1);
				jsonRetour.put("UUID", uuid);
				myHttpRequest.getJsonByPostWithJsonBody(applicationProperties.getString("notifier.bonus.boomcraft"),
						jsonRetour);
			}
		}

		// on retourne true si tout est ok
		jsonRetour = new org.json.simple.JSONObject();
		jsonRetour.put("succes", true);
		return Response.status(200).entity(jsonRetour.toJSONString()).build();
	}
}
