package com.api.service.others;

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
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.BonusDao;
import com.api.entitie.Bonus;
import com.api.utils.Utils;

/**
 * Classe permettant d'exposer les services pour le jeu boomcraft
 */
@Path("/boomcraft")
public class BoomcraftService {

	final static Logger logger = Logger.getLogger(BoomcraftService.class.getName());
	private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	/**
	 * Permet d'indiquer les potions disponibles pour un joueur dans boomcraft
	 * 
	 * @param uuid
	 * @return json
	 */
	@GET
	@Path("/potions/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPotionsByUuidGet(@PathParam("uuid") String uuid) {
		// controle du champ uuid
		if (uuid == null || uuid.equals("")) {
			return Response.status(200).entity(
					Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul"))
							.toString())
					.build();
		}

		// on consulte la base
		BonusDao bonusDao = new BonusDao();
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		listeBonus = bonusDao.getBonusByUuidAccount(uuid, "B");

		// on construit le json indiquant les bonus disponibles
		JSONArray jsonArray = new JSONArray();
		for (Bonus bonus : listeBonus) {
			jsonArray.put(bonus.getJsonForApi());
		}
		return Response.status(200).entity(jsonArray.toString()).build();
	}

	/**
	 * Permet de connaitre les potions consommées par un joueur dans boomcraft
	 * 
	 * @param uuid
	 * @return json
	 */
	@POST
	@Path("/potions/{uuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPotionsByUuidPost(@PathParam("uuid") String uuid, String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		// controle du champ uuid
		if (uuid == null || uuid.equals("")) {
			return Response.status(200).entity(
					Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul"))
							.toString())
					.build();
		}

		int idPotion = 0;
		int qtePotion = 0;
		// controle du champ idPotion
		if (jsonEnvoi.containsKey("id") && jsonEnvoi.get("id") != null) {
			Long id = (Long) jsonEnvoi.get("id");
			idPotion = id.intValue();
			if (idPotion == 0) {
				return Response.status(200)
						.entity(Utils
								.getJsonError("error", 500,
										applicationProperties.getString("message.erreur.identifiant.potion.nul"))
								.toString())
						.build();
			}
		}

		// controle du champ qte
		if (jsonEnvoi.containsKey("qte") && jsonEnvoi.get("qte") != null) {
			Long qte = (Long) jsonEnvoi.get("qte");
			qtePotion = qte.intValue();
			if (qtePotion <= 0) {
				return Response.status(200)
						.entity(Utils
								.getJsonError("error", 500,
										applicationProperties.getString("message.erreur.identifiant.potion.invalide"))
								.toString())
						.build();
			}
		}

		BonusDao bonusDao = new BonusDao();
		int nbEnbase = bonusDao.getQtePotionByUuidAndidPotion(idPotion, uuid);
		// on verifie que la qte n'est pas supérieure à la qte disponible
		if (qtePotion > nbEnbase) {
			return Response.status(200)
					.entity(Utils
							.getJsonError("error", 500,
									applicationProperties.getString("message.erreur.identifiant.potion.superieure"))
							.toString())
					.build();
		}

		Boolean retour = bonusDao.updateInventaireByUuid(nbEnbase - qtePotion, idPotion, uuid);

		// renvoi true si tout est ok
		JSONObject json = new JSONObject();
		try {
			json.put("success", !retour);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
}