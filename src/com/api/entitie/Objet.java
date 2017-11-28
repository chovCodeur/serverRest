package com.api.entitie;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Objet {
	
	private int id_objet;
	private String nom_objet;
	private TypeObjet type_objet;
	
	
	/**
	 * @param id_objet
	 * @param nom_objet
	 * @param type_objet
	 */
	public Objet(int id_objet, String nom_objet, TypeObjet type_objet) {
		this.id_objet = id_objet;
		this.nom_objet = nom_objet;
		this.type_objet = type_objet;
	}
	public Objet() {
	}
	public int getId_objet() {
		return id_objet;
	}
	public void setId_objet(int id_objet) {
		this.id_objet = id_objet;
	}
	
	public String getNom_objet() {
		return nom_objet;
	}
	public void setNom_objet(String nom_objet) {
		this.nom_objet = nom_objet;
	}
	public TypeObjet getType_objet() {
		return type_objet;
	}
	public void setType_objet(TypeObjet type_objet) {
		this.type_objet = type_objet;
	}
	
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id_objet", this.id_objet);
			json.put("nom_objet", this.nom_objet);
			json.put("type_objet", this.type_objet.getNom());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
}
