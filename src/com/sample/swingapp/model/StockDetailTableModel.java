package com.sample.swingapp.model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import com.sample.swingapp.yahoofinance.StockBean;

public class StockDetailTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "Label", "Value" };

	private StockBean stockBean = null;

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return 20;
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (stockBean != null) {
			if (col == 0 && row == 0) {
				return "TICKER";
			} else if (col == 1 && row == 0) {
				return stockBean.getTicker();
			} else if (col == 0 && row == 1) {
				return "Price:";
			} else if (col == 1 && row == 1) {
				return stockBean.getPrice();
			} else if (col == 0 && row == 2) {
				return "Change:";
			} else if (col == 1 && row == 2) {
				return stockBean.getChange();
			} else if (col == 0 && row == 3) {
				return "Open:";
			} else if (col == 1 && row == 3) {
				return stockBean.getOpen();
			} else if (col == 0 && row == 4) {
				return "Day's Range:";
			} else if (col == 1 && row == 4) {
				return stockBean.getDayRange();
			} else if (col == 0 && row == 5) {
				return "52wk Range:";
			} else if (col == 1 && row == 5) {
				return stockBean.get52WeeksRange();
			} else if (col == 0 && row == 6) {
				return "Prev Close:";
			} else if (col == 1 && row == 6) {
				return stockBean.getPrevClose();
			} else if (col == 0 && row == 7) {
				return "Volume:";
			} else if (col == 1 && row == 7) {
				return stockBean.getVolumne();
			} else if (col == 0 && row == 8) {
				return "Avg Vol (3m):";
			} else if (col == 1 && row == 8) {
				return stockBean.getAverageDailyVolume();
			} else if (col == 0 && row == 9) {
				return "Market Cap:";
			} else if (col == 1 && row == 9) {
				return stockBean.getMarketCapitalization();
			} else if (col == 0 && row == 10) {
				return "One Year Target Estimate:";
			} else if (col == 1 && row == 10) {
				return stockBean.getOneYearTargetEst();
			} else if (col == 0 && row == 11) {
				return "P/E (ttm):";
			} else if (col == 1 && row == 11) {
				return stockBean.getPeRatio();
			} else if (col == 0 && row == 12) {
				return "EPS (ttm):";
			} else if (col == 1 && row == 12) {
				return stockBean.getEps();
			} else if (col == 0 && row == 13) {
				return "Last Dividend Date:";
			} else if (col == 1 && row == 13) {
				return stockBean.getLastDividendDate();
			} else if (col == 0 && row == 14) {
				return "Next Earning Date:";
			} else if (col == 1 && row == 14) {
				return stockBean.getNextEarningDate();
			} else if (col == 0 && row == 15) {
				return "Last Refresh Time:";
			} else if (col == 1 && row == 15) {

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
						Locale.getDefault());

				return df.format(new Timestamp(stockBean.getLastUpdated()));
			}
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public void setStockBean(StockBean stockBean) {
		this.stockBean = stockBean;
	}

}
