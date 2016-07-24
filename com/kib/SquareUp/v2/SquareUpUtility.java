package com.kib.SquareUp.v2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import er.extensions.foundation.ERXProperties;

public class SquareUpUtility extends Object  {

	private static Logger log = Logger.getLogger(SquareUpUtility.class);
	
	public static final int NO_ERR = 0;
	public static final int MISC_ERR = 1;
	public static final int MESSAGE_EMPTY_ERR = 2;
	public static final int COMMAND_EMPTY_ERR = 3;
	
	public static final String COMMAND_LOCATIONS = "locations";
	public static final String COMMAND_CUSTOMERS = "customers";
	
	public static final String POST_METHOD_DELETE = "DELETE";
	public static final String POST_METHOD_PUT = "PUT";

	public static String composeAndSendSquareUpRequest(String command, String jsonContent) {
		return composeAndSendSquareUpRequest(command, jsonContent, null);
	}
	
	public static String composeAndSendSquareUpRequest(String command, String jsonContent, String postMethod) {
		String results = null;
		
		if (command == null || command.isEmpty()) {
			log.error(COMMAND_EMPTY_ERR + ": No command given.");
			return null;
		}

		int err = NO_ERR;
		
		log.debug("AccessToken: " + accessToken());

		// Sample curl: curl -H "Authorization: Bearer PERSONAL_ACCESS_TOKEN" https://connect.squareup.com/v2/locations
		String theURLToGet = String.format("https://connect.squareup.com/v2/%s", command);

		HttpURLConnection urlConnection = null;
		String postData = null;
		StringBuffer _pageContent = new StringBuffer();

		InputStream pageContentStream = null;
		BufferedReader pageReader = null;

		try {
			postData = (jsonContent == null || jsonContent.isEmpty()) ? null : jsonContent;

			URL webPage = new URL(theURLToGet);
			urlConnection = (HttpURLConnection)webPage.openConnection();
			urlConnection.setReadTimeout(15*1000);
			urlConnection.setUseCaches(false);
			urlConnection.setDoInput(true);
			
			if (postData == null || postData.isEmpty()) {
				if (postMethod == null) 
					urlConnection.setRequestMethod("GET");
				else
					urlConnection.setRequestMethod(postMethod);
			} else {
				urlConnection.setDoOutput(true);
				if (postMethod == null) 
					urlConnection.setRequestMethod("POST");
				else
					urlConnection.setRequestMethod(postMethod);
				urlConnection.setRequestProperty("Content-Type", "application/json");
				urlConnection.setRequestProperty("Content-Length", "" + postData.length());
				urlConnection.setFixedLengthStreamingMode(postData.length());
			}

			urlConnection.setRequestProperty("User-Agent", "KIBMembers");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("Authorization", String.format("Bearer %s", accessToken())); //Bearer

			Map<String, List<String>> reqhdrs = urlConnection.getRequestProperties();
			log.debug("> URL: " + urlConnection.getURL());
			log.debug("> Method: " + urlConnection.getRequestMethod());
		    Set<String> reqhdrKeys = reqhdrs.keySet();
		    for (String k : reqhdrKeys)
		      log.debug("> " + k + ": " + reqhdrs.get(k));

			log.info(urlConnection.getRequestMethod() + " " + theURLToGet);
		    urlConnection.connect();
								
			if (postData != null && !postData.isEmpty()) {
				log.info("Post data: " + postData);
				DataOutputStream stream = new DataOutputStream(urlConnection.getOutputStream());
				stream.writeBytes(postData);
				stream.close();
			}
			
			Integer responseCode = new Integer(urlConnection.getResponseCode());
			log.debug("Response: " + responseCode + " " + urlConnection.getResponseMessage());
			
			Map<String, List<String>> hdrs = urlConnection.getHeaderFields();
		    Set<String> hdrKeys = hdrs.keySet();
		    for (String k : hdrKeys)
		      log.debug("< " + k + ": " + hdrs.get(k));

		    try {
		    	pageContentStream = urlConnection.getInputStream();
		    } catch (Exception ex) {
				pageContentStream = urlConnection.getErrorStream();
		    }

			pageReader = new BufferedReader( new InputStreamReader(pageContentStream) );
			String pageLine = pageReader.readLine();
			while (pageLine != null) {
				_pageContent.append(pageLine);
				_pageContent.append("\n");
				pageLine = pageReader.readLine();
			}

			pageContentStream.close();
			results = _pageContent.toString();
						
		} catch (FileNotFoundException ex) {
			log.error(" - Page not found: " + ex.getMessage());
			log.error("   URL: " + theURLToGet);
		} catch (MalformedURLException ex) {
			log.error(" - MalformedURLException error: " + ex.getMessage());
			log.error("   URL: " + theURLToGet);
		} catch (UnsupportedEncodingException ex) {
			log.error(" - Failed to URLEncode " + postData);
			log.error("   using data AS-IS");
		} catch (UnknownServiceException ex) {
			log.error(" - Failed creating output stream for post data.");
		} catch (IOException ex) {
			log.error(" - IOException: " + ex.getMessage());
			log.error("   URL: " + theURLToGet);
		} finally {
			urlConnection.disconnect();
			log.debug("Connection closed.");
		}

		log.info("Response: " + results);

		return results;
	}

	public static String applicationId() {
		return ERXProperties.stringForKey("squareup.ApplicationId").trim();
	}
	
	public static String accessToken() {
		return ERXProperties.stringForKey("squareup.AccessToken").trim();
	}
	
	public static String applicationSecret() {
		return ERXProperties.stringForKey("squareup.ApplicationSecret").trim();
	}
}
