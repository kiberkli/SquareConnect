package com.kib.SquareUp.v2;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class Error {

	private static Logger log = Logger.getLogger(Error.class);

	public static final String CATEGORY_KEY = "category";
	public static final String CODE_KEY = "code";
	public static final String DETAIL_KEY = "detail";
	public static final String FIELD_KEY = "field";

	public String category;
	public String code;
	public String detail;
	public String field;

	public Error(JSON json) {
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(CATEGORY_KEY))
				category = ((JSONObject) json).getString(CATEGORY_KEY);
			if (((JSONObject) json).containsKey(CODE_KEY))
				category = ((JSONObject) json).getString(CODE_KEY);
			if (((JSONObject) json).containsKey(DETAIL_KEY))
				category = ((JSONObject) json).getString(DETAIL_KEY);
			if (((JSONObject) json).containsKey(FIELD_KEY))
				category = ((JSONObject) json).getString(FIELD_KEY);
		}
	}
	
	public void printError() {
		log.debug("Error:");
		log.debug(" category: " + category);
		log.debug(" code:     " + code);
		log.debug(" detail:   " + detail);
		log.debug(" field:    " + field);
	}
}
