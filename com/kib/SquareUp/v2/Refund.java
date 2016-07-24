package com.kib.SquareUp.v2;

import net.sf.json.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kib.SquareUp.v2.Refund;

public class Refund {

	private static Logger log = Logger.getLogger(Refund.class);

	public static final String ID_KEY = "id";
	public static final String LOCATION_ID_KEY = "location_id";
	public static final String TRANSACTION_ID_KEY = "transaction_id";
	public static final String TENDER_ID_KEY = "tender_id";
	public static final String CREATED_AT_KEY = "created_at";
	public static final String REASON_KEY = "reason";
	public static final String AMOUNT_MONEY_KEY = "amount_money";
	public static final String STATUS_KEY = "status";
	public static final String PROCESSING_FEE_MONEY_KEY = "processing_fee_money";

	public static final String IDEMPOTENCY_KEY = "idempotency_key";

	public String id;
	public String locationId;
	public String transactionId;
	public String tenderId;
	public String createdAt;
	public String reason;
	public Money amountMoney;
	public String status;
	public Money processingFeeMoney;

	public String idempotencyKey;

	public static Refund fromJSONObject (JSON json) {
		Refund results = new Refund();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ID_KEY))
				results.id = ((JSONObject) json).getString(ID_KEY);
			if (((JSONObject) json).containsKey(LOCATION_ID_KEY))
				results.locationId = ((JSONObject) json).getString(LOCATION_ID_KEY);
			if (((JSONObject) json).containsKey(TRANSACTION_ID_KEY))
				results.transactionId = ((JSONObject) json).getString(TRANSACTION_ID_KEY);
			if (((JSONObject) json).containsKey(TENDER_ID_KEY))
				results.tenderId = ((JSONObject) json).getString(TENDER_ID_KEY);
			if (((JSONObject) json).containsKey(CREATED_AT_KEY))
				results.createdAt = ((JSONObject) json).getString(CREATED_AT_KEY);
			if (((JSONObject) json).containsKey(REASON_KEY))
				results.reason = ((JSONObject) json).getString(REASON_KEY);
			if (((JSONObject) json).containsKey(AMOUNT_MONEY_KEY))
				results.amountMoney = Money.fromJSONObject(((JSONObject) json).getJSONObject(AMOUNT_MONEY_KEY));
			if (((JSONObject) json).containsKey(STATUS_KEY))
				results.status = ((JSONObject) json).getString(STATUS_KEY);
			if (((JSONObject) json).containsKey(PROCESSING_FEE_MONEY_KEY))
				results.processingFeeMoney = Money.fromJSONObject(((JSONObject) json).getJSONObject(PROCESSING_FEE_MONEY_KEY));

			if (((JSONObject) json).containsKey(IDEMPOTENCY_KEY))
				results.idempotencyKey = ((JSONObject) json).getString(IDEMPOTENCY_KEY);
}
		return results;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(ID_KEY, id);
		json.element(LOCATION_ID_KEY, locationId);
		json.element(TRANSACTION_ID_KEY, transactionId);
		json.element(TENDER_ID_KEY, tenderId);
		json.element(CREATED_AT_KEY, createdAt);
		json.element(REASON_KEY, reason);
		json.element(AMOUNT_MONEY_KEY, amountMoney.toJSONObject());
		json.element(STATUS_KEY, status);
		json.element(IDEMPOTENCY_KEY, idempotencyKey);
		if (processingFeeMoney != null) json.element(PROCESSING_FEE_MONEY_KEY, processingFeeMoney.toJSONObject());
		log.debug("Refund JSON: " + json.toString());
		return json;
	}
	
	public static List<Refund> getRefundList(JSON json) {
		List<Refund> results = new ArrayList<Refund>();
		if (json instanceof JSONArray) {
			results = new ArrayList<Refund>();
			JSONArray jsonArray = (JSONArray)json;
			for (Object obj : jsonArray) {
				Refund newRefund = Refund.fromJSONObject((JSON)obj);
				if (newRefund != null)
					results.add(newRefund);
			}
		}
		return results;
	}

	public void printRefund() {
		log.debug("Refund:");
		log.debug(" id:            "+id);
		log.debug(" locationId:    "+locationId);
		log.debug(" transactionId: "+transactionId);
		log.debug(" tenderId:      "+tenderId);
		log.debug(" createdAt:     "+createdAt);
		log.debug(" reason:        "+reason);
		log.debug(" status:        "+status);
		if (amountMoney != null) amountMoney.printMoney();
		if (processingFeeMoney != null) processingFeeMoney.printMoney();
	}

	public Refund sqCreateRefund(String locationId, String transactionId) throws Exception {
		Refund results = null;
		
		if (this.idempotencyKey == null || this.idempotencyKey.isEmpty()) {
			throw new Exception("The required idempotencyKey value is empty.");
		}
		
		String command = String.format("%s/%s/transactions/%s/refund", SquareUpUtility.COMMAND_LOCATIONS, locationId, transactionId);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, this.toJSONObject().toString());
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.refund != null) {
				squareUpResponse.refund.printRefund();
				results = squareUpResponse.refund;
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			}
		}

		return results;
	}
	
	public static List<Refund> sqListRefunds(String locationId) throws Exception {
		List<Refund> results = null;
		
		String command = String.format("%s/%s/refunds", SquareUpUtility.COMMAND_LOCATIONS, locationId);
		String squareResponseString = SquareUpUtility.composeAndSendSquareUpRequest(command, null);
		if (squareResponseString != null && !squareResponseString.isEmpty()) {
			SquareUpResponse squareUpResponse = new SquareUpResponse(squareResponseString);
			if (squareUpResponse != null && squareUpResponse.refundItems != null) {
				results = squareUpResponse.refundItems;
			}
			if (squareUpResponse != null && squareUpResponse.error != null) {
				squareUpResponse.error.printError();
				throw new Exception(squareUpResponse.error.code + " - " + squareUpResponse.error.category + ": " + squareUpResponse.error.detail);
			}
		}

		return results;
	}
}