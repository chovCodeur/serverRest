package com.api.entitie;

import java.sql.Timestamp;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.api.utils.Utils;

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
	
	public Account(int id, String id_global, String username, String email, String password, String faction, Timestamp created_at, Timestamp updated_at, Timestamp deleted_at) {
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

	public Account() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGlobalID() {
		return id_global;
	}

	public void setGlobalID(String globalID) {
		this.id_global = globalID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFaction() {
		return faction;
	}

	public void setFaction(String faction) {
		this.faction = faction;
	}

	public Timestamp getCreatedAT() {
		return created_at;
	}

	public void setCreatedAT(Timestamp createdAT) {
		this.created_at = createdAT;
	}

	public Timestamp getUpdatedAT() {
		return updated_at;
	}

	public void setUpdatedAT(Timestamp updatedAT) {
		this.updated_at = updatedAT;
	}

	public Timestamp getDeletedAT() {
		return deleted_at;
	}

	public void setDeletedAT(Timestamp deletedAT) {
		this.deleted_at = deletedAT;
	}
	
	@Override
	public String toString() {
		return "Account [id=" + id + ", globalID=" + id_global + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", faction=" + faction + ", createdAT=" + created_at + ", updatedAT="
				+ updated_at + ", deletedAT=" + deleted_at + "]";
	}
	
	public JSONObject getJsonForApi() {
		JSONObject json = new JSONObject();
		try {
			json.put("_id", this.id);
			if (Utils.testStringForJson(this.faction))  json.put("faction", this.faction);
			if (Utils.testStringForJson(this.email))  json.put("email", this.email);
			if (Utils.testStringForJson(this.username)) json.put("username", this.username);
			if (Utils.testStringForJson(this.id_global)) json.put("globalId", this.id_global);
			json.put("deletedAt", this.deleted_at);
			json.put("createdAt", this.created_at);
			json.put("updatedAt", this.updated_at);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}