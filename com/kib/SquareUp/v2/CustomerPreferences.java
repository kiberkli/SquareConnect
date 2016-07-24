package com.kib.SquareUp.v2;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class CustomerPreferences {

	private static Logger log = Logger.getLogger(CustomerPreferences.class);

	public static final String EMAIL_UNSUBSCRIBE_KEY = "email_unsubscribed";

	public Boolean emailUnsubscribed;

	public CustomerPreferences(JSON json) {
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(EMAIL_UNSUBSCRIBE_KEY))
				emailUnsubscribed = ((JSONObject) json).getBoolean(EMAIL_UNSUBSCRIBE_KEY);
		}
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.element(EMAIL_UNSUBSCRIBE_KEY, emailUnsubscribed);
		log.debug("CustomerPreferences JSON: " + json.toString());
		return json;
	}
}
