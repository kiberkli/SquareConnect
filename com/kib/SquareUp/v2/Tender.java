package com.kib.SquareUp.v2;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class Tender {

	private static Logger log = Logger.getLogger(Tender.class);

	public static final String ID_KEY = "id";
	public static final String LOCATION_ID_KEY = "location_id";
	public static final String TRANSACTION_ID_KEY = "transaction_id";
	public static final String CREATED_AT_KEY = "created_at";
	public static final String NOTE_KEY = "note";
	public static final String AMOUNT_MONEY_KEY = "amount_money";
	public static final String PROCESSING_FEE_MONEY_KEY = "processing_fee_money";
	public static final String CUSTOMER_ID_KEY = "customer_id";
	public static final String TYPE_KEY = "type";
	public static final String CARD_DETAILS_KEY = "card_details";
	public static final String CASH_DETAILS_KEY = "cash_details";

	public String id;
	public String locationId;
	public String transactionId;
	public String createdAt;
	public String note;
	public Money amountMoney;
	public Money processingFeeMoney;
	public String customerId;
	public String type;
	public TenderCardDetails cardDetails;
	public TenderCashDetails cashDetails;

	public static Tender fromJSONObject(JSON json) {
		Tender results = new Tender();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ID_KEY))
				results.id = ((JSONObject) json).getString(ID_KEY);
			if (((JSONObject) json).containsKey(LOCATION_ID_KEY))
				results.locationId = ((JSONObject) json).getString(LOCATION_ID_KEY);
			if (((JSONObject) json).containsKey(TRANSACTION_ID_KEY))
				results.transactionId = ((JSONObject) json).getString(TRANSACTION_ID_KEY);
			if (((JSONObject) json).containsKey(CREATED_AT_KEY))
				results.createdAt = ((JSONObject) json).getString(CREATED_AT_KEY);
			if (((JSONObject) json).containsKey(NOTE_KEY))
				results.note = ((JSONObject) json).getString(NOTE_KEY);
			if (((JSONObject) json).containsKey(AMOUNT_MONEY_KEY))
				results.amountMoney = Money.fromJSONObject(((JSONObject) json).getJSONObject(AMOUNT_MONEY_KEY));
			if (((JSONObject) json).containsKey(PROCESSING_FEE_MONEY_KEY))
				results.processingFeeMoney = Money.fromJSONObject(((JSONObject) json).getJSONObject(PROCESSING_FEE_MONEY_KEY));
			if (((JSONObject) json).containsKey(CUSTOMER_ID_KEY))
				results.customerId = ((JSONObject) json).getString(CUSTOMER_ID_KEY);
			if (((JSONObject) json).containsKey(TYPE_KEY))
				results.type = ((JSONObject) json).getString(TYPE_KEY);
			if (((JSONObject) json).containsKey(CARD_DETAILS_KEY))
				results.cardDetails = TenderCardDetails.fromJSONObject(((JSONObject) json).getJSONObject(CARD_DETAILS_KEY));
			if (((JSONObject) json).containsKey(CASH_DETAILS_KEY))
				results.cashDetails = TenderCashDetails.fromJSONObject(((JSONObject) json).getJSONObject(CASH_DETAILS_KEY));
		}
		return results;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(ID_KEY, id);
		json.element(LOCATION_ID_KEY, locationId);
		json.element(TRANSACTION_ID_KEY, transactionId);
		json.element(CREATED_AT_KEY, createdAt);
		json.element(NOTE_KEY, note);
		json.element(AMOUNT_MONEY_KEY, amountMoney.toJSONObject());
		json.element(PROCESSING_FEE_MONEY_KEY, processingFeeMoney.toJSONObject());
		json.element(CUSTOMER_ID_KEY, customerId);
		json.element(TYPE_KEY, type);
		json.element(CARD_DETAILS_KEY, cardDetails.toJSONObject());
		json.element(CASH_DETAILS_KEY, cashDetails.toJSONObject());
		log.debug("Tender JSON: " + json.toString());
		return json;
	}

	public static List<Tender> getTenderList(JSON json) {
		List<Tender> results = new ArrayList<Tender>();
		if (json instanceof JSONArray) {
			results = new ArrayList<Tender>();
			JSONArray jsonArray = (JSONArray) json;
			for (Object obj : jsonArray) {
				Tender newTender = Tender.fromJSONObject((JSON) obj);
				if (newTender != null)
					results.add(newTender);
			}
		}
		return results;
	}
	
	public void printTender() {
		log.debug("Tender:");
		log.debug(" id:                 " + id);
		log.debug(" locationId:         " + locationId);
		log.debug(" transactionId:      " + transactionId);
		log.debug(" createdAt:          " + createdAt);
		log.debug(" note:               " + note);
		log.debug(" amountMoney:        " + amountMoney);
		log.debug(" processingFeeMoney: " + processingFeeMoney);
		log.debug(" customerId:         " + customerId);
		if (amountMoney != null) amountMoney.printMoney();
		if (processingFeeMoney != null) processingFeeMoney.printMoney();
		if (cardDetails != null) cardDetails.printTenderCardDetails();
		if (cashDetails != null) cashDetails.printTenderCashDetails();
	}
}
