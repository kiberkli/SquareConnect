package com.kib.SquareUp.v2;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class CustomerCard {
	
	private static Logger log = Logger.getLogger(CustomerCard.class);

	public static final String CARD_NONCE_KEY = "card_nonce";
	public static final String BILLING_ADDRESS_KEY = "billing_address";
	public static final String CARD_HOLDER_NAME_KEY = "cardholder_name";
	
	public String cardNonce;
	public Address billingAddress;
	public String cardHolderName;
		
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(CARD_NONCE_KEY, cardNonce);
		if (billingAddress != null) json.element(BILLING_ADDRESS_KEY, billingAddress.toJSONObject());
		json.element(CARD_HOLDER_NAME_KEY, cardHolderName);
		return json;
	}

	public void printCustomerCard() {
		log.debug("CustomerCard:");
		log.debug(" cardNonce;      " + cardNonce);
		log.debug(" cardHolderName; " + cardHolderName);
		if (billingAddress != null) billingAddress.printAddress();
	}
	
	public Card sqCreateCustomerCard(String customerId) throws SquareUpException {
		Card results = null;

		if (this.cardNonce != null) {
			String command = String.format("%s/%s/cards", SquareUpUtility.COMMAND_CUSTOMERS, customerId);
			String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, this.toJSONObject().toString());
			if (squareResponseString != null && !squareResponseString.isEmpty()) {
				SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
				if (squareUpResponse != null && squareUpResponse.card != null) {
					squareUpResponse.card.printCard();
					results = squareUpResponse.card;
				}
				if (squareUpResponse != null && squareUpResponse.errorItems != null) {
					throw new SquareUpException(squareUpResponse.errorItems);
				} 
			}
		}
		
		return results;
	}

	public static void sqDeleteCustomerCard(String customerId, String cardId) throws SquareUpException {
		if (customerId != null && !customerId.isEmpty() && cardId != null && !cardId.isEmpty()) {
			String command = String.format("%s/%s/cards/%s", SquareUpUtility.COMMAND_CUSTOMERS, customerId, cardId);
			String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, null, SquareUpUtility.POST_METHOD_DELETE);
			if (squareResponseString != null && !squareResponseString.isEmpty()) {
				SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
				if (squareUpResponse != null && squareUpResponse.errorItems != null) {
					throw new SquareUpException(squareUpResponse.errorItems);
				}
			}
		}
	}


}
