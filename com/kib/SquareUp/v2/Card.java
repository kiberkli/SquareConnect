package com.kib.SquareUp.v2;

import net.sf.json.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class Card {

	private static Logger log = Logger.getLogger(Card.class);

	public static final String ID_KEY = "id";
	public static final String CARDBRAND_KEY = "card_brand";
	public static final String LAST4_KEY = "last_4";
	public static final String EXP_MONTH_KEY = "exp_month";
	public static final String EXP_YEAR_KEY = "exp_year";
	public static final String CARD_HOLDER_NAME = "cardholder_name";
	public static final String BILLING_ADDRESS = "billing_address";
	public static final String BILLING_POSTAL_CODE_KEY = "billing_postal_code";

	public String id;
	public String cardBrand;
	public String last4;
	public Integer expirationMonth;
	public Integer expirationYear;
	public String cardHolderName;
	public Address billingAddress;
	public String billingPostalCode;
	
	public static Card fromJSONObject(JSON json) {
		Card results = new Card();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ID_KEY))
				results.id = ((JSONObject) json).getString(ID_KEY);
			if (((JSONObject) json).containsKey(CARDBRAND_KEY))
				results.cardBrand = ((JSONObject) json).getString(CARDBRAND_KEY);
			if (((JSONObject) json).containsKey(LAST4_KEY))
				results.last4 = ((JSONObject) json).getString(LAST4_KEY);
			if (((JSONObject) json).containsKey(EXP_MONTH_KEY))
				results.expirationMonth = ((JSONObject) json).getInt(EXP_MONTH_KEY);
			if (((JSONObject) json).containsKey(EXP_YEAR_KEY))
				results.expirationYear = ((JSONObject) json).getInt(EXP_YEAR_KEY);
			if (((JSONObject) json).containsKey(CARD_HOLDER_NAME))
				results.cardHolderName = ((JSONObject) json).getString(CARD_HOLDER_NAME);
			if (((JSONObject) json).containsKey(BILLING_ADDRESS))
				results.billingAddress = Address.fromJSONObject(((JSONObject) json).getJSONObject(BILLING_ADDRESS));
			if (((JSONObject) json).containsKey(BILLING_POSTAL_CODE_KEY))
				results.billingPostalCode = ((JSONObject) json).getString(BILLING_POSTAL_CODE_KEY);
		}
		return results;
	}
	
	public static Card fromJSONString(String jsonString) {
		Card card = null;
		if (jsonString != null && !jsonString.isEmpty()) {
			JSON jsonCardResponse = JSONSerializer.toJSON(jsonString);
			card = Card.fromJSONObject(jsonCardResponse);
		}
		return card;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(ID_KEY, id);
		json.element(CARDBRAND_KEY, cardBrand);
		json.element(LAST4_KEY, last4);
		json.element(EXP_MONTH_KEY, expirationMonth);
		json.element(EXP_YEAR_KEY, expirationYear);
		json.element(CARD_HOLDER_NAME, cardHolderName);
		json.element(BILLING_POSTAL_CODE_KEY, billingPostalCode);
		if (billingAddress != null) json.element(BILLING_ADDRESS, billingAddress.toJSONObject());
		log.debug("Card JSON: " + json.toString());
		return json;
	}

	public void printCard() {
		log.debug("Card:");
		log.debug(" id:                " + id);
		log.debug(" cardBrand:         " + cardBrand);
		log.debug(" last4:             " + last4);
		log.debug(" expirationMonth:   " + expirationMonth);
		log.debug(" expirationYear:    " + expirationYear);
		log.debug(" cardHolderName:    " + cardHolderName);
		log.debug(" billingPostalCode: " + billingPostalCode);
		if (billingAddress != null) billingAddress.printAddress();
	}

	public static List<Card> getCardList(JSON json) {
		List<Card> results = new ArrayList<Card>();
		if (json instanceof JSONArray) {
			results = new ArrayList<Card>();
			JSONArray jsonArray = (JSONArray)json;
			for (Object obj : jsonArray) {
				Card newCard = Card.fromJSONObject((JSON)obj);
				if (newCard != null)
					results.add(newCard);
			}
		}
		return results;
	}

}
