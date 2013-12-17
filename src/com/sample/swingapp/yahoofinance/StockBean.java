package com.sample.swingapp.yahoofinance;

public class StockBean {
	String ticker;
	float price;
	float change;
	float open;
	String dayRange;
	long volumne;
	float eps;
	String _52WeeksRange;
	String marketCapitalization;
	String lastDividendDate;

	String stockExchange;
	float prevClose;
	float oneYearTargetEst;
	String nextEarningDate;

	long averageDailyVolume;

	float peRatio;

	String chartUrlSmall;
	String chartUrlLarge;
	long lastUpdated;

	public float getPeRatio() {
		return peRatio;
	}

	public void setPeRatio(float peRatio) {
		this.peRatio = peRatio;
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

	public float getEps() {
		return eps;
	}

	public void setEps(float eps) {
		this.eps = eps;
	}

	public String get52WeeksRange() {
		return _52WeeksRange;
	}

	public void set52WeeksRange(String _52WeeksRange) {
		this._52WeeksRange = _52WeeksRange;
	}

	public String getMarketCapitalization() {
		return marketCapitalization;
	}

	public void setMarketCapitalization(String marketCapitalization) {
		this.marketCapitalization = marketCapitalization;
	}

	public String getLastDividendDate() {
		return lastDividendDate;
	}

	public void setLastDividendDate(String lastDividendDate) {
		this.lastDividendDate = lastDividendDate;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String yahooStockInfo) {
		this.stockExchange = yahooStockInfo;
	}

	public float getPrevClose() {
		return prevClose;
	}

	public void setPrevClose(float prevClose) {
		this.prevClose = prevClose;
	}

	public float getOneYearTargetEst() {
		return oneYearTargetEst;
	}

	public void setOneYearTargetEst(float oneYearTargetEst) {
		this.oneYearTargetEst = oneYearTargetEst;
	}

	public String getNextEarningDate() {
		return nextEarningDate;
	}

	public void setNextEarningDate(String nextEarningDate) {
		this.nextEarningDate = nextEarningDate;
	}

	public long getVolumne() {
		return volumne;
	}

	public void setVolumne(long volumne) {
		this.volumne = volumne;
	}

	public long getAverageDailyVolume() {
		return averageDailyVolume;
	}

	public void setAverageDailyVolume(long averageDailyVolume) {
		this.averageDailyVolume = averageDailyVolume;
	}

}