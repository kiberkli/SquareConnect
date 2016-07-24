package com.kib.SquareUp.v2;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class Location {

	private static Logger log = Logger.getLogger(Location.class);

	public static final String ID_KEY = "id";
	public static final String NAME_KEY = "name";
	public static final String TIMEZONE_KEY = "timezone";
	public static final String CAPABILITIES_KEY = "capabilities";
	public static final String ADDRESS_KEY = "address";

	public String id;
	public String name;
	public Address address;
	public String timeZone;
	public List<String> capabilities;

	public Location(JSON json) {
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ID_KEY))
				id = ((JSONObject) json).getString(ID_KEY);
			if (((JSONObject) json).containsKey(NAME_KEY))
				name = ((JSONObject) json).getString(NAME_KEY);
			if (((JSONObject) json).containsKey(TIMEZONE_KEY))
				timeZone = ((JSONObject) json).getString(TIMEZONE_KEY);
			if (((JSONObject) json).containsKey(ADDRESS_KEY))
				address = Address.fromJSONObject(((JSONObject) json).getJSONObject(ADDRESS_KEY));
			if (((JSONObject) json).containsKey(CAPABILITIES_KEY)) {
				JSONArray value = ((JSONObject) json).getJSONArray(CAPABILITIES_KEY);
				capabilities = new ArrayList<String>();
				for (int i = 0; i < value.size(); i++)
					capabilities.add(value.getString(i));
			}
		}
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.element(ID_KEY, id);
		json.element(NAME_KEY, name);
		json.element(TIMEZONE_KEY, timeZone);
		json.element(ADDRESS_KEY, address.toJSONObject());
		for (String capability : capabilities) {
			json.accumulate(CAPABILITIES_KEY, capability);
		}
		log.debug("Location JSON: " + json.toString());
		return json;
	}
	
	public void printLocation() {
		log.debug("Location:");
		log.debug(" id:           " + id);
		log.debug(" name:         " + name);
		log.debug(" timeZone:     " + timeZone);
		log.debug(" capabilities: " + capabilities);
		if (address != null) address.printAddress();
	}

}
