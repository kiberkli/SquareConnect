package com.kib.SquareUp.v2;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.*;

import org.apache.log4j.Logger;

import com.kib.SquareUp.v2.Refund;
import com.kib.SquareUp.v2.Tender;

public class Transaction {

	private static Logger log = Logger.getLogger(Transaction.class);

	public static final String ID_KEY = "id";
	public static final String LOCATION_ID_KEY = "location_id";
	public static final String CREATED_AT_KEY = "created_at";
	public static final String TENDERS_KEY = "tenders";
	public static final String REFUNDS_KEY = "refunds";
	public static final String REFERENCE_ID_KEY = "reference_id";
	public static final String PRODUCT_KEY = "product";
	public static final String CLIENT_ID_KEY = "client_id";

	public String id;
	public String locationId;
	public String createdAt;
	public List<Tender> tenders;
	public List<Refund> refunds;
	public String referenceId;
	public String product;
	public String clientId;

	public Transaction() {
		tenders = new ArrayList<Tender>();
		refunds = new ArrayList<Refund>();
	}
	
	public static Transaction fromJSONObject(JSON json) {
		log.debug(json.toString());
		Transaction results = new Transaction();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ID_KEY))
				results.id = ((JSONObject) json).getString(ID_KEY);
			if (((JSONObject) json).containsKey(LOCATION_ID_KEY))
				results.locationId = ((JSONObject) json).getString(LOCATION_ID_KEY);
			if (((JSONObject) json).containsKey(CREATED_AT_KEY))
				results.createdAt = ((JSONObject) json).getString(CREATED_AT_KEY);
			if (((JSONObject) json).containsKey(TENDERS_KEY))
				results.tenders = Tender.getTenderList(((JSONObject) json).getJSONArray(TENDERS_KEY));
			if (((JSONObject) json).containsKey(REFUNDS_KEY))
				results.refunds = Refund.getRefundList(((JSONObject) json).getJSONArray(REFUNDS_KEY));
			if (((JSONObject) json).containsKey(REFERENCE_ID_KEY))
				results.referenceId = ((JSONObject) json).getString(REFERENCE_ID_KEY);
			if (((JSONObject) json).containsKey(PRODUCT_KEY))
				results.product = ((JSONObject) json).getString(PRODUCT_KEY);
			if (((JSONObject) json).containsKey(CLIENT_ID_KEY))
				results.clientId = ((JSONObject) json).getString(CLIENT_ID_KEY);
		}
		return results;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(ID_KEY, id);
		json.element(LOCATION_ID_KEY, locationId);
		json.element(CREATED_AT_KEY, createdAt);
		for (Tender tender : tenders) {
			json.accumulate(TENDERS_KEY, tender.toJSONObject());
		}
		for (Refund refund : refunds) {
			json.accumulate(REFUNDS_KEY, refund.toJSONObject());
		}
		json.element(REFERENCE_ID_KEY, referenceId);
		json.element(PRODUCT_KEY, product);
		json.element(CLIENT_ID_KEY, clientId);
		log.debug("Transaction JSON: " + json.toString());
		return json;
	}
	
	public void printTransaction() {
		log.debug("Transaction:");
		log.debug(" id:          " + id);
		log.debug(" locationId:  " + locationId);
		log.debug(" createdAt:   " + createdAt);
		log.debug(" referenceId: " + referenceId);
		log.debug(" product:     " + product);
		log.debug(" clientId:    " + clientId);

		if (tenders != null)
			for (Tender tender : tenders) {
				tender.printTender();
			}

		if (refunds != null)
			for (Refund refund : refunds) {
				refund.printRefund();
			}
	}

	public static List<Transaction> sqListTransactions(String locationId) throws Exception {
		List<Transaction> results = null;
		
		String command = String.format("%s/%s/transactions", SquareUpUtility.COMMAND_LOCATIONS, locationId);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, null);
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.transactionItems != null) {
				results = squareUpResponse.transactionItems;
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			}
		}

		return results;
	}
}
