package com.kib.SquareUp.v2;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class Charge {

	private static Logger log = Logger.getLogger(Charge.class);

	public static final String IDEMPOTENCY_KEY = "idempotency_key";
	public static final String AMOUNT_MONEY_KEY = "amount_money";
	public static final String CARD_NONCE_KEY = "card_nonce";
	public static final String CUSTOMER_CARD_ID_KEY = "customer_card_id";
	public static final String DELAY_CAPTURE_KEY = "delay_capture";
	public static final String REFERENCE_ID_KEY = "reference_id";
	public static final String NOTE_KEY = "note";
	public static final String CUSTOEMR_ID_KEY = "customer_id";
	public static final String BILLING_ADDRESS_KEY = "billing_address";
	public static final String SHIPPING_ADDRESS_KEY = "shipping_address";
	public static final String BUYER_EMAIL_ADDRESS_KEY = "buyer_email_address";

	public String idempotencyKey;
	public Money amount;
	public String cardNonce;
	public String cardId;
	public Boolean delayCapture;
	public String referecneId;
	public String note;
	public String customerId;
	public Address billingAddress;
	public Address shippingAddress;
	public String buyerEmailAddress;
		
	public Charge() {
		amount = new Money();
		delayCapture = false;
	}
	
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(IDEMPOTENCY_KEY, idempotencyKey);
		if (amount != null) json.element(AMOUNT_MONEY_KEY, amount.toJSONObject());
		json.element(CARD_NONCE_KEY, cardNonce);
		json.element(CUSTOMER_CARD_ID_KEY, cardId);
		json.element(DELAY_CAPTURE_KEY, delayCapture);
		json.element(REFERENCE_ID_KEY, referecneId);
		json.element(NOTE_KEY, note);
		json.element(CUSTOEMR_ID_KEY, customerId);
		if (billingAddress != null) json.element(BILLING_ADDRESS_KEY, billingAddress.toJSONObject());
		if (shippingAddress != null) json.element(SHIPPING_ADDRESS_KEY, shippingAddress.toJSONObject());
		json.element(BUYER_EMAIL_ADDRESS_KEY, buyerEmailAddress);
		log.debug("Card JSON: " + json.toString());
		return json;
	}
	
	public void printCharge() {
		log.debug("Charge:");
		log.debug(" idempotencyKey:    " + idempotencyKey);
		if (amount != null) amount.printMoney();
		log.debug(" cardNonce:         " + cardNonce);
		log.debug(" cardId:            " + cardId);
		log.debug(" cardNonce:         " + cardNonce);
		log.debug(" delayCapture:      " + delayCapture);
		log.debug(" referecneId:       " + referecneId);
		log.debug(" note:              " + note);
		log.debug(" customerId:        " + customerId);
		if (billingAddress != null) billingAddress.printAddress();
		if (shippingAddress != null) shippingAddress.printAddress();
		log.debug(" buyerEmailAddress: " + buyerEmailAddress);
	}

	public Transaction sqTransaction(String squareLocationId) throws Exception {
		Transaction results = null;
		
		if ((buyerEmailAddress == null || buyerEmailAddress.isEmpty()) && (billingAddress == null || shippingAddress == null))
			throw new Exception("Required values are missing, please provide an buyer email address and a shipping/billing address.");

		String command = String.format("%s/%s/transactions", SquareUpUtility.COMMAND_LOCATIONS, squareLocationId);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, this.toJSONObject().toString());
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareResponse = new SquareUpResponse(squareResponseString);
			if (squareResponse != null && squareResponse.transaction != null) {
				results = squareResponse.transaction;
				results.printTransaction();
			} else if(squareResponse.error != null) {
				squareResponse.error.printError();
				throw new Exception(squareResponse.error.code + " - " + squareResponse.error.category + ": " + squareResponse.error.detail);
			} else
				log.info("No location object returned.");
		}
		
		return results;
	}

}
