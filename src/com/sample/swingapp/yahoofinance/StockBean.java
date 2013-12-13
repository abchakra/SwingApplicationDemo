package com.sample.swingapp.yahoofinance;

public class StockBean {
	String ticker;
	float price;
	float change;
	float open;
	String dayRange;
	String chartUrlSmall;
	String chartUrlLarge;
	long lastUpdated;

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public String getDayRange() {
		return dayRange;
	}

	public void setDayRange(String dayRange) {
		this.dayRange = dayRange;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getChange() {
		return change;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public String getChartUrlSmall() {
		return chartUrlSmall;
	}

	public void setChartUrlSmall(String chartUrlSmall) {
		this.chartUrlSmall = chartUrlSmall;
	}

	public String getChartUrlLarge() {
		return chartUrlLarge;
	}

	public void setChartUrlLarge(String chartUrlLarge) {
		this.chartUrlLarge = chartUrlLarge;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}