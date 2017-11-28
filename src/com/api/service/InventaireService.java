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

import com.api.dao.InventaireDao;
import com.api.entitie.Inventaire;

@Path("/inventaire")
public class InventaireService {

	final static Logger logger = Logger.getLogger(InventaireService.class.getName());

	@GET
	@Path("/getAllInventaires")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInventaires() {
		logger.info("Appel TEST_DEV getAllInventaires");
		InventaireDao inventaireDao = new InventaireDao();

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		logger.debug("MiPa avant appel DAO");
		inventaires = inventaireDao.getAllInventaires();
		logger.info("Création du JSON ");

		JSONObject json = new JSONObject();
		try {
			int i = 1;
			for (Inventaire inventaire : inventaires) {
				json.put(String.valueOf(i), inventaire.getJson());
				i++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		logger.debug("JSON  créé : " + json.toString());
		return Response.status(200).entity(json.toString()).build();
	}
}
