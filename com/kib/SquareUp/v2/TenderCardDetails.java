package com.kib.SquareUp.v2;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class TenderCardDetails {

	private static Logger log = Logger.getLogger(TenderCardDetails.class);

	public static final String STATUS_KEY = "status";
	public static final String CARD_KEY = "card";
	public static final String ENTRY_METHOD_KEY = "entry_method";

	public String status;
	public Card card;
	public String entryMethod;

	public static TenderCardDetails fromJSONObject(JSON json) {
		TenderCardDetails results = new TenderCardDetails();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(STATUS_KEY))
				results.status = ((JSONObject) json).getString(STATUS_KEY);
			if (((JSONObject) json).containsKey(CARD_KEY))
				results.card = Card.fromJSONObject(((JSONObject) json).getJSONObject(CARD_KEY));
			if (((JSONObject) json).containsKey(ENTRY_METHOD_KEY))
				results.entryMethod = ((JSONObject) json).getString(ENTRY_METHOD_KEY);
		}
		return results;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(STATUS_KEY, status);
		json.element(CARD_KEY, card.toJSONObject());
		json.element(ENTRY_METHOD_KEY, entryMethod);
		log.debug("TenderCardDetails JSON: " + json.toString());
		return json;
	}
	
	public void printTenderCardDetails() {
		log.debug("TenderCardDetails:");
		log.debug(" status:      "+status);
		log.debug(" entryMethod: "+entryMethod);
		if (card != null) card.printCard();
	}
}
