package com.api.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.FactionDao;
import com.api.entitie.Faction;

@Path("/faction")
public class FactionService {

	final static Logger logger = Logger.getLogger(FactionService.class.getName());

	@GET
	@Path("/getAllFactions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFactions() {
		logger.info("Appel TEST_DEV getAllFactions");
		FactionDao factionDao = new FactionDao();

		ArrayList<Faction> factions = new ArrayList<Faction>();
		logger.debug("MiPa avant appel DAO");
		factions = factionDao.getAllFactions();
		logger.info("Création du JSON ");

		JSONObject json = new JSONObject();
		try {
			for (Faction faction : factions) {
				json.put(String.valueOf(faction.getId()), faction.getJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
}
