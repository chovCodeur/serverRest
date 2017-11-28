package com.api.service;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.io.InputStream;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

@Path("/users")
public class UtilisateurService {
	final static Logger logger = Logger.getLogger(UtilisateurService.class.getName());

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") int id) {
		logger.info("Appel getUser");

		logger.info("Création du JSON ");
		JSONObject json = new JSONObject();
		String result = json.toString();
		logger.debug("JSON  créé : " + result);

		return Response.status(200).entity(result).build();
	}

	@GET
	@Path("/pingAPI")
	@Produces(MediaType.TEXT_PLAIN)
	public Response testReponse(InputStream incomingData) {
		logger.info("Appel testReponse");
		String result = "L'API est a l'ecoute";
		return Response.status(200).entity(result).build();
	}

	@POST
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response postUser(@PathParam("id") int id) {
		logger.info("Appel postUser");
		return Response.status(200).entity("OK").build();
	}
}