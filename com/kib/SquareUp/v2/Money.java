package com.kib.SquareUp.v2;

import java.math.BigDecimal;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class Money {

	private static Logger log = Logger.getLogger(Money.class);

	public static final String AMOUNT_KEY = "amount";
	public static final String CURRENCY_KEY = "currency";

	public Integer amount; // For USD this is in cents
	public String currency;

	public Money() {
		currency = "USD";
	}

	public Money(BigDecimal anAmount) {
		currency = "USD";
		amount = (int)Math.round(anAmount.doubleValue() * 100);
	}

	public BigDecimal amount() {
		double results = amount / 100;
		return new BigDecimal(results);
	}
	
	public static Money fromJSONObject(JSON json) {
		Money result = new Money();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(AMOUNT_KEY))
				result.amount = ((JSONObject) json).getInt(AMOUNT_KEY);
			if (((JSONObject) json).containsKey(AMOUNT_KEY))
				result.currency = ((JSONObject) json).getString(AMOUNT_KEY);
		}
		return result;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(AMOUNT_KEY, amount);
		json.element(CURRENCY_KEY, currency);
		log.debug("Money JSON: " + json.toString());
		return json;
	}
	
	public void printMoney() {
		log.debug("Money:");
		log.debug(" amount:   " + amount);
		log.debug(" currency: " + currency);
	}
}
