package com.api.service;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.AccountDao;
import com.api.dao.InventaireDao;
import com.api.entitie.Account;
import com.api.entitie.Inventaire;
import com.api.utils.Utils;

@Path("/inventaire")
public class InventaireService {

	final static Logger logger = Logger.getLogger(InventaireService.class.getName());

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInventaires() {
		InventaireDao inventaireDao = new InventaireDao();

		ArrayList<Inventaire> inventaires = new ArrayList<Inventaire>();
		inventaires = inventaireDao.getAllInventaires();

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
		return Response.status(200).entity(json.toString()).build();
	}
	
	@POST
	@Path("/potion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response testExistAccount(String value) {
		org.json.simple.JSONObject jsonEnvoi = new org.json.simple.JSONObject();
		jsonEnvoi = Utils.parseJsonObject(value);
		
		JSONObject jsonRetour = new JSONObject();
		Account account = new Account();
		
		String uuid = new String();
		if (jsonEnvoi.containsKey("uuid") && jsonEnvoi.get("uuid") != null) {
			uuid = (String) jsonEnvoi.get("uuid");
			//account = accountDao.getAccountBy
			if (account != null && account.getId() != 0) {
			}
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
