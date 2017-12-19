package com.api.entitie;

import java.sql.Timestamp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.utils.Utils;

/**
 * Classe permettant de gérer un ACCOUNT
 */
public class Account {
	private int id;
	private String id_global;
	private String username;
	private String email;
	private String password;
	private String faction;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Timestamp deleted_at;

	/**
	 * Constructeur
	 * 
	 * @param id
	 * @param id_global
	 * @param username
	 * @param email
	 * @param password
	 * @param faction
	 * @param created_at
	 * @param updated_at
	 * @param deleted_at
	 */
	public Account(int id, String id_global, String username, String email, String password, String faction,
			Timestamp created_at, Timestamp updated_at, Timestamp deleted_at) {
		this.id = id;
		this.id_global = id_global;
		this.username = username;
		this.email = email;
		this.password = password;
		this.faction = faction;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.deleted_at = deleted_at;
	}

	/**
	 * Constructeur par défaut
	 */
	public Account() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the id_global
	 */
	public String getId_global() {
		return id_global;
	}

	/**
	 * @param id_global
	 *            the id_global to set
	 */
	public void setId_global(String id_global) {
		this.id_global = id_global;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the faction
	 */
	public String getFaction() {
		return faction;
	}

	/**
	 * @param faction
	 *            the faction to set
	 */
	public void setFaction(String faction) {
		this.faction = faction;
	}

	/**
	 * @return the created_at
	 */
	public Timestamp getCreated_at() {
		return created_at;
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	/**
	 * @return the updated_at
	 */
	public Timestamp getUpdated_at() {
		return updated_at;
	}

	/**
	 * @param updated_at
	 *            the updated_at to set
	 */
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	/**
	 * @return the deleted_at
	 */
	public Timestamp getDeleted_at() {
		return deleted_at;
	}

	/**
	 * @param deleted_at
	 *            the deleted_at to set
	 */
	public void setDeleted_at(Timestamp deleted_at) {
		this.deleted_at = deleted_at;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Account [id=" + id + ", globalID=" + id_global + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", faction=" + faction + ", createdAT=" + created_at + ", updatedAT="
				+ updated_at + ", deletedAT=" + deleted_at + "]";
	}

	/**
	 * Permet de construire un objet JSON avec tous les champs
	 * 
	 * @return JSONObject
	 */
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", this.id);
			if (Utils.testStringForJson(this.id_global))
				json.put("id_global", this.id_global);
			if (Utils.testStringForJson(this.username))
				json.put("username", this.username);
			if (Utils.testStringForJson(this.email))
				json.put("email", this.email);
			if (Utils.testStringForJson(this.password))
				json.put("password", this.password);
			if (Utils.testStringForJson(this.faction))
				json.put("faction", this.faction);
			json.put("created_at", this.created_at);
			json.put("updated_at", this.updated_at);
			json.put("deleted_at", this.deleted_at);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * Permet de construire un objet JSON avec tous les champs à renvoyer pour les
	 * autres API
	 * 
	 * @return JSONObject
	 */
	public JSONObject getJsonForApi() {
		JSONObject json = new JSONObject();
		try {
			json.put("_id", this.id);
			if (Utils.testStringForJson(this.faction)) {
				json.put("faction", this.faction);
			}
			if (Utils.testStringForJson(this.email)) {
				json.put("email", this.email);
			}
			if (Utils.testStringForJson(this.username)) {
				json.put("username", this.username);
			}
			if (Utils.testStringForJson(this.id_global)) {
				json.put("globalId", this.id_global);
			}
			json.put("deletedAt", this.deleted_at);
			json.put("createdAt", this.created_at);
			json.put("updatedAt", this.updated_at);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}