package com.kib.SquareUp.v2;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.*;

import org.apache.log4j.Logger;

import com.kib.SquareUp.v2.Card;

public class Customer {

	private static Logger log = Logger.getLogger(Customer.class);
	
	public static final String ID_KEY = "id";
	public static final String CREATED_AT_KEY = "created_at";
	public static final String UPDATED_AT_KEY = "updated_at";
	public static final String CARDS_KEY = "cards";
	public static final String GIVEN_NAME_KEY = "given_name";
	public static final String FAMILY_NAME_KEY = "family_name";
	public static final String NICKNAME_KEY = "nickname";
	public static final String COMPANY_NAME_KEY = "company_name";
	public static final String EMAIL_ADDRESS_KEY = "email_address";
	public static final String ADDRESS_KEY = "address";
	public static final String PHONE_NUMBER_KEY = "phone_number";
	public static final String REFERENCE_ID_KEY = "reference_id";
	public static final String PREFERENCES_KEY = "preferences";
	public static final String NOTE_KEY = "note";

	public String id;
	public String createdAt;
	public String updatedAt;
	public List<Card> cards;
	public String givenName;
	public String familyName;
	public String nickname;
	public String companyName;
	public String emailAddress;
	public Address address;
	public String phoneNumber;
	public String referenceId;
	public String preferences;
	public String note;
	
	public Customer() {
		cards = new ArrayList<Card>();
	}
	
	public static Customer fromJSONObject(JSON json) {
		log.debug(json.toString());
		Customer results = new Customer();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ID_KEY))
				results.id = ((JSONObject) json).getString(ID_KEY);
			if (((JSONObject) json).containsKey(CREATED_AT_KEY))
				results.createdAt = ((JSONObject) json).getString(CREATED_AT_KEY);
			if (((JSONObject) json).containsKey(UPDATED_AT_KEY))
				results.updatedAt = ((JSONObject) json).getString(UPDATED_AT_KEY);
			if (((JSONObject) json).containsKey(CARDS_KEY))
				results.cards = Card.getCardList(((JSONObject) json).getJSONArray(CARDS_KEY));
			if (((JSONObject) json).containsKey(GIVEN_NAME_KEY))
				results.givenName = ((JSONObject) json).getString(GIVEN_NAME_KEY);
			if (((JSONObject) json).containsKey(FAMILY_NAME_KEY))
				results.familyName = ((JSONObject) json).getString(FAMILY_NAME_KEY);
			if (((JSONObject) json).containsKey(NICKNAME_KEY))
				results.nickname = ((JSONObject) json).getString(NICKNAME_KEY);
			if (((JSONObject) json).containsKey(COMPANY_NAME_KEY))
				results.companyName = ((JSONObject) json).getString(COMPANY_NAME_KEY);
			if (((JSONObject) json).containsKey(EMAIL_ADDRESS_KEY))
				results.emailAddress = ((JSONObject) json).getString(EMAIL_ADDRESS_KEY);
			if (((JSONObject) json).containsKey(ADDRESS_KEY))
				results.address = Address.fromJSONObject(((JSONObject) json).getJSONObject(ADDRESS_KEY));
			if (((JSONObject) json).containsKey(PHONE_NUMBER_KEY))
				results.phoneNumber = ((JSONObject) json).getString(PHONE_NUMBER_KEY);
			if (((JSONObject) json).containsKey(REFERENCE_ID_KEY))
				results.referenceId = ((JSONObject) json).getString(REFERENCE_ID_KEY);
			if (((JSONObject) json).containsKey(PREFERENCES_KEY))
				results.preferences = ((JSONObject) json).getString(PREFERENCES_KEY);
			if (((JSONObject) json).containsKey(NOTE_KEY))
				results.note = ((JSONObject) json).getString(NOTE_KEY);
			
			if (results.companyName == null)
				results.companyName = results.nickname;
		}
		return results;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(ID_KEY, id);
		json.element(CREATED_AT_KEY, createdAt);
		json.element(UPDATED_AT_KEY, updatedAt);
		for (Card card : cards) {
			json.accumulate(CARDS_KEY, card.toJSONObject());
		}
		json.element(GIVEN_NAME_KEY, givenName);
		json.element(FAMILY_NAME_KEY, familyName);
		json.element(NICKNAME_KEY, nickname);
		json.element(COMPANY_NAME_KEY, companyName);
		json.element(EMAIL_ADDRESS_KEY, emailAddress);
		if (address != null) json.element(ADDRESS_KEY, address.toJSONObject());
		json.element(PHONE_NUMBER_KEY, phoneNumber);
		json.element(REFERENCE_ID_KEY, referenceId);
		json.element(PREFERENCES_KEY, preferences);
		json.element(NOTE_KEY, note);
		log.debug("Customer JSON: " + json.toString());
		return json;
	}
	
	public void addCard(Card aValue) {
		cards.add(aValue);
	}
	
	public void printCustomer() {
		log.debug("Customer:");
		log.debug(" id:           " + id);
		log.debug(" companyName:  " + companyName);
		log.debug(" createdAt:    " + createdAt);
		log.debug(" emailAddress: " + emailAddress);
		log.debug(" familyName:   " + familyName);
		log.debug(" givenName:    " + givenName);
		log.debug(" nickname:     " + nickname);
		log.debug(" note:         " + note);
		log.debug(" phoneNumber:  " + phoneNumber);
		log.debug(" preferences:  " + preferences);
		log.debug(" referenceId:  " + referenceId);
		log.debug(" updatedAt:    " + updatedAt);
		if (address != null) address.printAddress();
	}

	public Customer sqCreateCustomer() throws Exception {
		Customer results = null;
		
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(SquareUpUtility.COMMAND_CUSTOMERS, this.toJSONObject().toString());
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.customer != null) {
				squareUpResponse.customer.printCustomer();
				results = squareUpResponse.customer;
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			}
		}

		return results;
	}

	public static Customer sqRetrieveCustomer(String customerId) throws Exception {
		Customer results = null;
		
		String command = String.format("%s/%s", SquareUpUtility.COMMAND_CUSTOMERS, customerId);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, null);
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.customer != null) {
				squareUpResponse.customer.printCustomer();
				results = squareUpResponse.customer;
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			}
		}

		return results;
	}
	
	public static List<Customer> sqRetrieveCustomers() throws Exception {
		List<Customer> results = new ArrayList<Customer>();
		
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(SquareUpUtility.COMMAND_CUSTOMERS, null);
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.customerItems != null) {
				results = squareUpResponse.customerItems;
				for(Customer customer : results)
					customer.printCustomer();
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			} 
		}

		return results;
	}
	
	public static void sqDeleteCustomer(String customerId)  throws Exception {
		String command = String.format("%s/%s", SquareUpUtility.COMMAND_CUSTOMERS, customerId);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, null, SquareUpUtility.POST_METHOD_DELETE);
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			} 
		}
	}

	public Customer sqUpdateCustomer() throws Exception {
		Customer results = null;
		
		String command = String.format("%s/%s", SquareUpUtility.COMMAND_CUSTOMERS, this.id);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, this.toJSONObject().toString(), SquareUpUtility.POST_METHOD_PUT);
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.customer != null) {
				squareUpResponse.customer.printCustomer();
				results = squareUpResponse.customer;
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			} 
		}

		return results;
	}
}
