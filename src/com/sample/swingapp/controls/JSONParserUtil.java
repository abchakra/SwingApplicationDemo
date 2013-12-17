package com.sample.swingapp.controls;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParserUtil {

	public static void searchStockCodeByCompanyName(String companyName)
			throws Exception {

		System.setProperty("java.net.useSystemProxies", "true");
		System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
		System.setProperty("http.proxyPort", "80");
		String request = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query="
				+ companyName
				+ "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";

		URL yahoofin = new URL(request);
		URLConnection yc = yahoofin.openConnection();
		// Process the response from Yahoo! Web Services
		BufferedReader br = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String jsonString = "";
		String line;
		while ((line = br.readLine()) != null) {
			jsonString += line;
		}
		br.close();

		// Construct a JSONObject from a source JSON text string.
		// A JSONObject is an unordered collection of name/value pairs. Its
		// external
		// form is a string wrapped in curly braces with colons between the
		// names
		// and values, and commas between the values and names.

		String toRemovePrefix = "YAHOO.Finance.SymbolSuggest.ssCallback(";
		jsonString = jsonString.replace(toRemovePrefix, "");
		jsonString = jsonString.substring(0, jsonString.length() - 1);

		JSONObject jo = new JSONObject(jsonString);

		// A JSONArray is an ordered sequence of values. Its external form is a
		// string wrapped in square brackets with commas between the values.
		JSONArray ja;

		// Get the JSONObject value associated with the search result key.
		jo = jo.getJSONObject("ResultSet");

		// System.out.println(jo.toString());

		// Get the JSONArray value associated with the Result key
		ja = jo.getJSONArray("Result");

		// Get the number of search results in this set
		int resultCount = ja.length();

		// Loop over each result and print the title, summary, and URL
		for (int i = 0; i < resultCount; i++) {
			// {"typeDisp":"Equity","symbol":"GOOG","name":"Google Inc.","type":"S","exchDisp":"NASDAQ","exch":"NMS"}
			JSONObject resultObject = ja.getJSONObject(i);
			System.out.println(resultObject.get("typeDisp"));
			System.out.println(resultObject.get("symbol"));
			System.out.println(resultObject.get("type"));
			System.out.println(resultObject.get("name"));
			System.out.println(resultObject.get("exchDisp"));
			System.out.println(resultObject.get("exch"));
			System.out.println("--");
		}
	}
}