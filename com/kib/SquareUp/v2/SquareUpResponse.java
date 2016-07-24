package com.kib.SquareUp.v2;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class SquareUpResponse {
	
	private static Logger log = Logger.getLogger(SquareUpResponse.class);

	public static final String LOCATION_KEY = "locations";
	public static final String ERROR_KEY = "error";
	public static final String CARD_KEY = "card";
	public static final String CUSTOMER_KEY = "customer";
	public static final String CUSTOMERS_KEY = "customers";
	public static final String TRANSACTION_KEY = "transaction";
	public static final String TRANSACTIONS_KEY = "transactions";
	public static final String REFUND_KEY = "refund";
	public static final String REFUNDS_KEY = "refunds";

	public Error error = null;
	public List<Location> locationItems = null;
	public Card card = null;
	public Customer customer = null;
	public Transaction transaction = null;
	public Refund refund = null;
	public List<Customer> customerItems = null;
	public List<Transaction> transactionItems = null;
	public List<Refund> refundItems = null;

	public SquareUpResponse(String jsonResponse) {
		
		JSON json = JSONSerializer.toJSON(jsonResponse);

		if (json instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)json;
			
			if (jsonObject.containsKey(ERROR_KEY)) {
				JSON value = (JSON)jsonObject.get(ERROR_KEY);
				if (value instanceof JSONObject) {
					error = new Error(value);
				}
			}

			if (jsonObject.containsKey(LOCATION_KEY)) {
				JSON value = (JSON)jsonObject.get(LOCATION_KEY);
				if (value instanceof JSONArray) {
					locationItems = new ArrayList<Location>();
					JSONArray jsonArray = (JSONArray)value;
					for (Object obj : jsonArray) {
						Location newLocation = new Location((JSON)obj);
						if (newLocation != null)
							locationItems.add(newLocation);
					}
				}
			}
			
			if (jsonObject.containsKey(TRANSACTIONS_KEY)) {
				JSON value = (JSON)jsonObject.get(TRANSACTIONS_KEY);
				if (value instanceof JSONArray) {
					transactionItems = new ArrayList<Transaction>();
					JSONArray jsonArray = (JSONArray)value;
					for (Object obj : jsonArray) {
						Transaction newTransaction = Transaction.fromJSONObject((JSON)obj);
						if (newTransaction != null)
							transactionItems.add(newTransaction);
					}
				}
			}
			
			if (jsonObject.containsKey(CUSTOMERS_KEY)) {
				JSON value = (JSON)jsonObject.get(CUSTOMERS_KEY);
				if (value instanceof JSONArray) {
					customerItems = new ArrayList<Customer>();
					JSONArray jsonArray = (JSONArray)value;
					for(Object obj : jsonArray) {
						Customer newCustomer = Customer.fromJSONObject((JSON)obj);
						if (newCustomer != null)
							customerItems.add(newCustomer);
					}
				}
			}
			
			if (jsonObject.containsKey(REFUNDS_KEY)) {
				JSON value = (JSON)jsonObject.get(REFUNDS_KEY);
				if (value instanceof JSONArray) {
					refundItems = new ArrayList<Refund>();
					JSONArray jsonArray = (JSONArray)value;
					for(Object obj : jsonArray) {
						Refund newRefund = Refund.fromJSONObject((JSON)obj);
						if (newRefund != null)
							refundItems.add(newRefund);
					}
				}
			}
						
			if (jsonObject.containsKey(CARD_KEY)) {
				JSON value = (JSON)jsonObject.get(CARD_KEY);
				if (value instanceof JSONObject) {
					card = Card.fromJSONObject(value);
				}
			}
			
			if (jsonObject.containsKey(CUSTOMER_KEY)) {
				JSON value = (JSON)jsonObject.get(CUSTOMER_KEY);
				if (value instanceof JSONObject) {
					customer = Customer.fromJSONObject(value);
				}
			}
			
			if (jsonObject.containsKey(TRANSACTION_KEY)) {
				JSON value = (JSON)jsonObject.get(TRANSACTION_KEY);
				if (value instanceof JSONObject) {
					transaction = Transaction.fromJSONObject(value);
				}
			}
			
			if (jsonObject.containsKey(REFUND_KEY)) {
				JSON value = (JSON)jsonObject.get(REFUND_KEY);
				if (value instanceof JSONObject) {
					refund = Refund.fromJSONObject(value);
				}
			}
		}
	}
		
	public void printLocations() {
		for (Location location : locationItems) {
			location.printLocation();
		}
	}
}
