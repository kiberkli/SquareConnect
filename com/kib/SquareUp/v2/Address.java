package com.kib.SquareUp.v2;

import net.sf.json.*;

import org.apache.log4j.Logger;

public class Address {

	private static Logger log = Logger.getLogger(Address.class);

	public static final String ADDRESS_KEY1 = "address_line_1";
	public static final String ADDRESS_KEY2 = "address_line_2";
	public static final String ADDRESS_KEY3 = "address_line_3";
	public static final String LOCALITY_KEY = "locality";
	public static final String SUBLOCALITY_KEY1 = "sublocality";
	public static final String SUBLOCALITY_KEY2 = "sublocality_2";
	public static final String SUBLOCALITY_KEY3 = "sublocality_3";
	public static final String ADMIN_DISTRICT_LEVEL_STATE_KEY1 = "administrative_district_level_1";
	public static final String ADMIN_DISTRICT_LEVEL_COUNTY_KEY2 = "administrative_district_level_2";
	public static final String ADMIN_DISTRICT_LEVEL_KEY3 = "administrative_district_level_3";
	public static final String POSTAL_CODE_KEY = "postal_code";
	public static final String COUNTRY_KEY = "country";

	public String address1;
	public String address2;
	public String address3;
	public String locality;
	public String subLocality1;
	public String subLocality2;
	public String subLocality3;
	public String adminDistrictLevel1; // state;
	public String adminDistrictLevel2; // county;
	public String adminDistrictLevel3;
	public String postalCode;
	public String country;

	public static Address fromJSONObject(JSONObject json) {
		Address results = new Address();
		if (json instanceof JSONObject) {
			if (((JSONObject) json).containsKey(ADDRESS_KEY1))
				results.address1 = ((JSONObject) json).getString(ADDRESS_KEY1);
			if (((JSONObject) json).containsKey(ADDRESS_KEY2))
				results.address2 = ((JSONObject) json).getString(ADDRESS_KEY2);
			if (((JSONObject) json).containsKey(ADDRESS_KEY3))
				results.address3 = ((JSONObject) json).getString(ADDRESS_KEY3);
			if (((JSONObject) json).containsKey(LOCALITY_KEY))
				results.locality = ((JSONObject) json).getString(LOCALITY_KEY);
			if (((JSONObject) json).containsKey(SUBLOCALITY_KEY1))
				results.subLocality1 = ((JSONObject) json).getString(SUBLOCALITY_KEY1);
			if (((JSONObject) json).containsKey(SUBLOCALITY_KEY2))
				results.subLocality2 = ((JSONObject) json).getString(SUBLOCALITY_KEY2);
			if (((JSONObject) json).containsKey(SUBLOCALITY_KEY3))
				results.subLocality3 = ((JSONObject) json).getString(SUBLOCALITY_KEY3);
			if (((JSONObject) json).containsKey(ADMIN_DISTRICT_LEVEL_STATE_KEY1))
				results.adminDistrictLevel1 = ((JSONObject) json).getString(ADMIN_DISTRICT_LEVEL_STATE_KEY1);
			if (((JSONObject) json).containsKey(ADMIN_DISTRICT_LEVEL_COUNTY_KEY2))
				results.adminDistrictLevel2 = ((JSONObject) json).getString(ADMIN_DISTRICT_LEVEL_COUNTY_KEY2);
			if (((JSONObject) json).containsKey(ADMIN_DISTRICT_LEVEL_KEY3))
				results.adminDistrictLevel3 = ((JSONObject) json).getString(ADMIN_DISTRICT_LEVEL_KEY3);
			if (((JSONObject) json).containsKey(POSTAL_CODE_KEY))
				results.postalCode = ((JSONObject) json).getString(POSTAL_CODE_KEY);
			if (((JSONObject) json).containsKey(COUNTRY_KEY))
				results.country = ((JSONObject) json).getString(COUNTRY_KEY);
		}
		return results;
	}

	public String state() {
		return adminDistrictLevel1;
	}
	
	public void setState(String aValue) {
		adminDistrictLevel1 = aValue;
	}

	public String county() {
		return adminDistrictLevel2;
	}

	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.element(ADDRESS_KEY1, address1);
		json.element(ADDRESS_KEY2, address2);
		json.element(ADDRESS_KEY3, address3);
		json.element(LOCALITY_KEY, locality);
		json.element(SUBLOCALITY_KEY1, subLocality1);
		json.element(SUBLOCALITY_KEY2, subLocality2);
		json.element(SUBLOCALITY_KEY3, subLocality3);
		json.element(ADMIN_DISTRICT_LEVEL_STATE_KEY1, adminDistrictLevel1);
		json.element(ADMIN_DISTRICT_LEVEL_COUNTY_KEY2, adminDistrictLevel2);
		json.element(ADMIN_DISTRICT_LEVEL_KEY3, adminDistrictLevel3);
		json.element(POSTAL_CODE_KEY, postalCode);
		json.element(COUNTRY_KEY, country);
		log.debug("Address JSON: " + json.toString());
		return json;
	}

	public void printAddress() {
		log.debug("Address:");
		log.debug(" address 1:           " + address1);
		log.debug(" address 2:           " + address2);
		log.debug(" address 3:           " + address3);
		log.debug(" locality:            " + locality);
		log.debug(" sublocality 1:       " + subLocality1);
		log.debug(" sublocality 1:       " + subLocality2);
		log.debug(" sublocality 1:       " + subLocality3);
		log.debug(" state:               " + state());
		log.debug(" county:              " + county());
		log.debug(" adminDistrictLevel3: " + adminDistrictLevel3);
		log.debug(" postalCode:          " + postalCode);
		log.debug(" coutry:              " + country);
	}

}
