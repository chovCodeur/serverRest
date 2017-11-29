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
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFactions() {
		FactionDao factionDao = new FactionDao();

		ArrayList<Faction> factions = new ArrayList<Faction>();
		factions = factionDao.getAllFactions();

		JSONObject json = new JSONObject();
		try {
			for (Faction faction : factions) {
				json.put(String.valueOf(faction.getId()), faction.getJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
}
