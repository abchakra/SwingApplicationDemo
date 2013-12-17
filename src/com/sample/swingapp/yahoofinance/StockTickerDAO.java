package com.sample.swingapp.yahoofinance;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StockTickerDAO {
	private static final Log log = LogFactory.getLog(StockTickerDAO.class);
	private static HashMap<String, StockBean> stocks = new HashMap<String, StockBean>();
	private static final long TWENTY_MIN = 1200000;

	private static class StockTickerDAOHolder {
		public static final StockTickerDAO INSTANCE = new StockTickerDAO();

	}

	public static StockTickerDAO getInstance() {
		return StockTickerDAOHolder.INSTANCE;
	}

	protected Object readResolve() {
		return getInstance();
	}

	/**
	 * 
	 * @param symbol
	 * @return StockBean will return null if unable to retrieve information
	 */
	public StockBean getStockPrice(String symbol) {
		StockBean stock;
		long currentTime = (new Date()).getTime();
		// Check last updated and only pull stock on average every 20 minutes
		if (stocks.containsKey(symbol)) {
			stock = stocks.get(symbol);
			if (currentTime - stock.getLastUpdated() > TWENTY_MIN) {
				stock = refreshStockInfo(symbol, currentTime);
			}
		} else {
			stock = refreshStockInfo(symbol, currentTime);
		}
		return stock;
	}

	/**
	 * 
	 * @param symbol
	 *            in format TICKER+TICKER+...
	 * @return StockBean will return null if unable to retrieve information
	 */
	public List<StockBean> getStockBeans(String symbol) {

		long currentTime = (new Date()).getTime();
		// Check last updated and only pull stock on average every 20 minutes
		List<StockBean> stockBeans = refreshStockBeansInfo(symbol, currentTime);

		return stockBeans;
	}

	private synchronized List<StockBean> refreshStockBeansInfo(String symbol,
			long currentTime) {

		List<StockBean> stockBeans = new ArrayList<StockBean>();
		try {
			URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s="
					+ symbol + "&f=sl1d1t1c1ohgv&e=.csv");
			URLConnection yc = yahoofin.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String[] yahooStockInfo = inputLine.split(",");
				StockBean stockInfo = new StockBean();
				stockInfo.setTicker(yahooStockInfo[0].replaceAll("\"", ""));
				stockInfo.setPrice(Float.valueOf(yahooStockInfo[1]));
				stockInfo.setChange(Float.valueOf(yahooStockInfo[4]));

				stockInfo.setOpen(Float.valueOf(yahooStockInfo[5]));
				stockInfo.setDayRange(Float.valueOf(yahooStockInfo[7]) + " - "
						+ yahooStockInfo[6]);

				stockInfo
						.setChartUrlSmall("http://ichart.finance.yahoo.com/t?s="
								+ stockInfo.getTicker());
				stockInfo
						.setChartUrlLarge("http://chart.finance.yahoo.com/w?s="
								+ stockInfo.getTicker());
				stockInfo.setLastUpdated(currentTime);
				stockBeans.add(stockInfo);
				stocks.put(stockInfo.getTicker(), stockInfo);
			}
			in.close();
		} catch (Exception ex) {
			log.error("Unable to get stockinfo for: " + symbol + ex);
		}
		return stockBeans;
	}

	// This is synched so we only do one request at a time
	// If yahoo doesn't return stock info we will try to return it from the map
	// in memory
	private synchronized StockBean refreshStockInfo(String symbol, long time) {
		try {
			URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s="
					+ symbol + "&f=sl1d1t1c1ohgv&e=.csv");
			URLConnection yc = yahoofin.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String[] yahooStockInfo = inputLine.split(",");
				StockBean stockInfo = new StockBean();
				stockInfo.setTicker(yahooStockInfo[0].replaceAll("\"", ""));
				stockInfo.setPrice(Float.valueOf(yahooStockInfo[1]));
				stockInfo.setChange(Float.valueOf(yahooStockInfo[4]));

				stockInfo.setOpen(Float.valueOf(yahooStockInfo[5]));
				stockInfo.setDayRange(Float.valueOf(yahooStockInfo[7]) + " - "
						+ yahooStockInfo[6]);

				stockInfo
						.setChartUrlSmall("http://ichart.finance.yahoo.com/t?s="
								+ stockInfo.getTicker());
				stockInfo
						.setChartUrlLarge("http://chart.finance.yahoo.com/w?s="
								+ stockInfo.getTicker());
				stockInfo.setLastUpdated(time);
				stocks.put(symbol, stockInfo);
				break;
			}
			in.close();
		} catch (Exception ex) {
			log.error("Unable to get stockinfo for: " + symbol + ex);
		}
		return stocks.get(symbol);
	}

	// This is synched so we only do one request at a time
	// If yahoo doesn't return stock info we will try to return it from the map
	// in memory
	private synchronized void refreshStockBeans(String symbol, long time) {
		try {
			URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s="
					+ symbol + "&f=sl1c1hgv&e=.csv");
			URLConnection yc = yahoofin.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String[] yahooStockInfo = inputLine.split(",");
				String ticker = yahooStockInfo[0].replaceAll("\"", "");
				StockBean stockInfo = stocks.get(ticker);
				stockInfo.setPrice(Float.valueOf(yahooStockInfo[1]));
				stockInfo.setChange(Float.valueOf(yahooStockInfo[2]));
				stockInfo.setDayRange(Float.valueOf(yahooStockInfo[4]) + " - "
						+ yahooStockInfo[3]);
				stockInfo.setLastUpdated(time);
			}
			in.close();
		} catch (Exception ex) {
			log.error("Unable to get stockinfo for: " + symbol + ex);
		}
	}

	public void refresStockBeans(List<StockBean> stockBeans, String symbol) {

		long currentTime = (new Date()).getTime();
		// Check last updated and only pull stock on average every 20 minutes
		refreshStockBeans(symbol, currentTime);

	}

	public void loadAllForStockBean(String symbol) {
		long currentTime = (new Date()).getTime();
		StockBean stockBean = stocks.get(symbol);
		if (stockBean != null) {
			loadAllForStockBean(stockBean, currentTime);

		}
	}

	private synchronized void loadAllForStockBean(StockBean stockBean,
			long currentTime) {

		try {
			URL yahoofin = new URL("http://finance.yahoo.com/d/quotes.csv?s="
					+ stockBean.getTicker()
					+ "&f=sl1c1hgve7jkj1qxpt8r1a2r2&e=.csv");
			URLConnection yc = yahoofin.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				String[] yahooStockInfo = inputLine.split(",");
				String ticker = yahooStockInfo[0].replaceAll("\"", "");
				StockBean stockInfo = stocks.get(ticker);
				stockInfo.setPrice(Float.valueOf(yahooStockInfo[1]));
				stockInfo.setChange(Float.valueOf(yahooStockInfo[2]));
				stockInfo.setDayRange(Float.valueOf(yahooStockInfo[4]) + " - "
						+ yahooStockInfo[3]);
				stockInfo.setVolumne(Long.valueOf(yahooStockInfo[5]));
				stockInfo.setEps(Float.valueOf(yahooStockInfo[6]));
				stockInfo.set52WeeksRange(Float.valueOf(yahooStockInfo[8])
						+ " - " + Float.valueOf(yahooStockInfo[7]));
				stockInfo.setMarketCapitalization(yahooStockInfo[9]);
				stockInfo.setLastDividendDate(yahooStockInfo[10]);
				stockInfo.setStockExchange(yahooStockInfo[11]);
				stockInfo.setPrevClose(Float.valueOf(yahooStockInfo[12]));
				stockInfo
						.setOneYearTargetEst(Float.valueOf(yahooStockInfo[13]));
				stockInfo.setNextEarningDate(yahooStockInfo[14]);
				stockInfo.setAverageDailyVolume(Long
						.valueOf(yahooStockInfo[15]));

				stockInfo.setLastUpdated(currentTime);
			}
			in.close();
		} catch (Exception ex) {
			log.error("Unable to get stockinfo for: " + stockBean.getTicker()
					+ ex);
		}

	}
}