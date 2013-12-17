package com.sample.swingapp.yahoofinacne.json;

import java.util.List;

import javax.xml.transform.Result;

import org.codehaus.jackson.annotate.JsonProperty;

public class ResultSet {
	private String query;
	private List<Result> result;

	public String getQuery() {
		return this.query;
	}

	@JsonProperty("Query")
	public void setQuery(String query) {
		this.query = query;
	}

	public List<Result> getResult() {
		return this.result;
	}

	@JsonProperty("Result")
	public void setResult(List<Result> result) {
		this.result = result;
	}
}