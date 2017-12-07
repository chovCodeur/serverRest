package com.api.entitie;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.utils.Utils;

public class Bonus {
	int id_objet;
	int qte;
	String description_recette;
	int puissance_objet;
	String type_objet;
	
	
	public Bonus () {
		
	}

	@Override
	public String toString() {
		return "Bonus [id_objet=" + id_objet + ", qte=" + qte + ", description_recette=" + description_recette
				+ ", puissance_objet=" + puissance_objet + ", type_objet=" + type_objet + ", nom_recette=" + nom_recette
				+ "]";
	}
	
	/**
	 * @param id_objet
	 * @param qte
	 * @param description_recette
	 * @param puissance_objet
	 * @param type_objet
	 * @param nom_recette
	 */
	public Bonus(int id_objet, int qte, String description_recette, int puissance_objet, String type_objet,
			String nom_recette) {
		this.id_objet = id_objet;
		this.qte = qte;
		this.description_recette = description_recette;
		this.puissance_objet = puissance_objet;
		this.type_objet = type_objet;
		this.nom_recette = nom_recette;
	}
	String nom_recette;
	/**
	 * @return the id_objet
	 */
	public int getId_objet() {
		return id_objet;
	}
	/**
	 * @param id_objet the id_objet to set
	 */
	public void setId_objet(int id_objet) {
		this.id_objet = id_objet;
	}
	/**
	 * @return the qte
	 */
	public int getQte() {
		return qte;
	}
	/**
	 * @param qte the qte to set
	 */
	public void setQte(int qte) {
		this.qte = qte;
	}
	/**
	 * @return the nom_recette
	 */
	public String getNom_recette() {
		return nom_recette;
	}
	/**
	 * @param nom_recette the nom_recette to set
	 */
	public void setNom_recette(String nom_recette) {
		this.nom_recette = nom_recette;
	}
	/**
	 * @return the description_recette
	 */
	public String getDescription_recette() {
		return description_recette;
	}
	/**
	 * @param description_recette the description_recette to set
	 */
	public void setDescription_recette(String description_recette) {
		this.description_recette = description_recette;
	}
	/**
	 * @return the puissance_objet
	 */
	public int getPuissance_objet() {
		return puissance_objet;
	}
	/**
	 * @param puissance_objet the puissance_objet to set
	 */
	public void setPuissance_objet(int puissance_objet) {
		this.puissance_objet = puissance_objet;
	}
	/**
	 * @return the type_objet
	 */
	public String getType_objet() {
		return type_objet;
	}
	/**
	 * @param type_objet the type_objet to set
	 */
	public void setType_objet(String type_objet) {
		this.type_objet = type_objet;
	}
	
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", this.id_objet);
			json.put("puissance", this.puissance_objet);
			if (Utils.testStringForJson(this.nom_recette)) json.put("nom", this.nom_recette);
			if (Utils.testStringForJson(this.description_recette)) json.put("description", this.description_recette);
			json.put("qte", this.qte);
			
			if (this.nom_recette.contains("soins")) {
				json.put("type", "health");
			} else if (this.nom_recette.contains("magique")) {
				json.put("type", "magic");
			} else if (this.nom_recette.contains("dégâts")) {
				json.put("type", "attack");
			} else if (this.nom_recette.contains("défense")) {
				json.put("type", "defense");
			} else if (this.nom_recette.contains("pierre")) {
				json.put("type", "pierre");
			} else if (this.nom_recette.contains("d'or")) {
				json.put("type", "or");
			} else if (this.nom_recette.contains("nourriture")) {
				json.put("type", "nourriture");
			} else if (this.nom_recette.contains("bois")) {
				json.put("type", "bois");
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
