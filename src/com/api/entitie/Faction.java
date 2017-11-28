package com.api.entitie;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Faction {
	private int id;
	private String name;
	
	public Faction(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Faction() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", this.id);
			json.put("name", this.name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
}
