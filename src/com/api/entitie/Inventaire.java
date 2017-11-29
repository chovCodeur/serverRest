package com.api.entitie;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Inventaire {

	private int id_user;
	private int id_objet;
	private int qte;
	
	
	/**
	 * @param id_user
	 * @param id_objet
	 * @param quantite
	 */
	public Inventaire(int id_user, int id_objet, int quantite) {
		this.id_user = id_user;
		this.id_objet = id_objet;
		this.qte = quantite;
	}
	
	public Inventaire () {
		
	}
	
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getId_objet() {
		return id_objet;
	}
	public void setId_objet(int id_objet) {
		this.id_objet = id_objet;
	}
	public int getQuantite() {
		return qte;
	}
	public void setQuantite(int quantite) {
		this.qte = quantite;
	}
	
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id_user", this.id_user);
			json.put("id_objet", this.id_objet);
			json.put("qte", this.qte);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public String toString() {
		return "Inventaire [id_user=" + id_user + ", id_objet=" + id_objet + ", qte=" + qte + "]";
	}
}