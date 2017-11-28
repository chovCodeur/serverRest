package com.api.entitie;

import java.sql.Date;
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
	private int id_faction;
	private Timestamp created_at;
	private Timestamp updated_at;
	private Timestamp deleted_at;
	
	public Account(int id, String id_global, String username, String email, String password, int id_faction, Timestamp created_at, Timestamp updated_at, Timestamp deleted_at) {
		this.id = id;
		this.id_global = id_global;
		this.username = username;
		this.email = email;
		this.password = password;
		this.id_faction = id_faction;
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

	public int getId_faction() {
		return id_faction;
	}

	public void setId_faction(int id_faction) {
		this.id_faction = id_faction;
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
				+ ", password=" + password + ", id_faction=" + id_faction + ", createdAT=" + created_at + ", updatedAT="
				+ updated_at + ", deletedAT=" + deleted_at + "]";
	}

	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", this.id);
			if (Utils.testStringForJson(this.id_global)) json.put("id_global", this.id_global);
			if (Utils.testStringForJson(this.username)) json.put("username", this.username);
			if (Utils.testStringForJson(this.email))  json.put("email", this.email);
			if (Utils.testStringForJson(this.password)) json.put("password", this.password);
			json.put("id_faction", this.id_faction);
			json.put("created_at", this.created_at);
			json.put("updated_at", this.updated_at);
			json.put("deleted_at", this.deleted_at);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

}