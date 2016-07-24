package com.kib.SquareUp.v2;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class TenderCashDetails {

	private static Logger log = Logger.getLogger(TenderCashDetails.class);

	public static final String BUYER_TENDER_MONEY_KEY = "buyer_tendered_money";
	public static final String CHANGE_BACK_MONEY_KEY = "change_back_money";

	public Money buyerTenderedMoney;
	public Money changeBackMoney;

	public static TenderCashDetails fromJSONObject(JSON json) {
		TenderCashDetails results = new TenderCashDetails();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(BUYER_TENDER_MONEY_KEY))
				results.buyerTenderedMoney = Money.fromJSONObject(((JSONObject) json).getJSONObject(BUYER_TENDER_MONEY_KEY));
			if (((JSONObject) json).containsKey(CHANGE_BACK_MONEY_KEY))
				results.changeBackMoney = Money.fromJSONObject(((JSONObject) json).getJSONObject(CHANGE_BACK_MONEY_KEY));
		}
		return results;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(BUYER_TENDER_MONEY_KEY, buyerTenderedMoney.toJSONObject());
		json.element(CHANGE_BACK_MONEY_KEY, changeBackMoney.toJSONObject());
		log.debug("TenderCashDetails JSON: " + json.toString());
		return json;
	}
	
	public void printTenderCashDetails() {
		log.debug("TenderCashDetails:");
		buyerTenderedMoney.printMoney();
		changeBackMoney.printMoney();
	}
}
