package com.api.service.others;

import java.io.IOException;
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

import com.api.dao.AccountDao;
import com.api.dao.BonusDao;
import com.api.entitie.Account;
import com.api.entitie.Bonus;
import com.api.http.MyHttpRequest;
import com.api.service.AccountService;
import com.api.utils.Utils;

@Path("/farmvillage")
public class FarmvillageService {

    private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

	@GET
	@Path("/potions/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPotionsByUuidGet(@PathParam("uuid") String uuid) {
		if (uuid == null || uuid.equals("")) {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul")).toString()).build();
		}
		
		BonusDao bonusDao = new BonusDao();
		ArrayList<Bonus> listeBonus = new ArrayList<Bonus>();
		
		listeBonus = bonusDao.getBonusByUuidAccount(uuid, "F");
		
		JSONArray jsonArray = new JSONArray();
		
		for (Bonus bonus : listeBonus) {
			jsonArray.put(bonus.getJsonForApi());
		}
		
		return Response.status(200).entity(jsonArray.toString()).build();
	}
	
	@POST
	@Path("/potions/{uuid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPotionsByUuidPost (@PathParam("uuid") String uuid, String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		
		if (uuid == null || uuid.equals("")) {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.nul")).toString()).build();
		}
		
		BonusDao bonusDao = new BonusDao();

		int idPotion = 0;
		int qtePotion = 0;
		if (jsonEnvoi.containsKey("id") && jsonEnvoi.get("id") != null) {
			Long id = (Long) jsonEnvoi.get("id");
			idPotion = id.intValue();
			if (idPotion <= 0) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.potion.nul")).toString()).build();
			}
		}

		if (jsonEnvoi.containsKey("qte") && jsonEnvoi.get("qte") != null) {
			Long qte = (Long) jsonEnvoi.get("qte");
			qtePotion = qte.intValue();
			if (qtePotion  <= 0) {
				return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.potion.invalide")).toString()).build();
			}
		}
		
		int nbEnbase = bonusDao.getQtePotionByUuidAndidPotion(idPotion, uuid);

		if (qtePotion > nbEnbase) {
			return Response.status(200).entity(Utils.getJsonError("error", 500, applicationProperties.getString("message.erreur.identifiant.potion.superieure")).toString()).build();
		}
		
		Boolean retour = bonusDao.updateInventaireByUuid(nbEnbase-qtePotion, idPotion, uuid);
		
		JSONObject json = new JSONObject();
		try {
			json.put("success", !retour);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return Response.status(200).entity(json.toString()).build();
	}
}
