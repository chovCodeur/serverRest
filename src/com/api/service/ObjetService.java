package com.api.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.dao.ObjetDao;
import com.api.entitie.Objet;

@Path("/objet")
public class ObjetService {
	
	final static Logger logger = Logger.getLogger(InventaireService.class.getName());

	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllObjets() {
		ObjetDao objetDao = new ObjetDao();

		ArrayList<Objet> objets = new ArrayList<Objet>();
		objets = objetDao.getAllObjets();

		JSONObject json = new JSONObject();
		try {
			for (Objet objet : objets) {
				json.put(String.valueOf(objet.getId_objet()), objet.getJson());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
	

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") String id) {
		ObjetDao objetDao = new ObjetDao();

		ArrayList<Objet> objets = new ArrayList<Objet>();
		objets = objetDao.getObjetByIdAccount(id);

		JSONObject json = new JSONObject();
		try {
			for (Objet objet : objets) {
					json.put(String.valueOf(objet.getId_objet()), objet.getJson());
				}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(json.toString()).build();
	}
}