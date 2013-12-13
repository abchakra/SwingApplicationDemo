package com.sample.swingapp.editor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.table.AbstractTableModel;

import com.sample.swingapp.yahoofinance.StockBean;

public class StockTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "TICKER", "Price", "Change",
			"Last Updated", "Open", " Day's Range" };

	private List<StockBean> stocks = new ArrayList<>();

	public StockTableModel(List<StockBean> stocks) {
		this.stocks = stocks;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return stocks.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (col == 0) {
			return stocks.get(row).getTicker();
		} else if (col == 1) {
			return stocks.get(row).getPrice();
		} else if (col == 2) {
			return stocks.get(row).getChange();
		} else if (col == 3) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
					Locale.getDefault());

			return df.format(new Timestamp(stocks.get(row).getLastUpdated()));
		} else if (col == 4) {
			return stocks.get(row).getOpen();
		} else if (col == 5) {
			return stocks.get(row).getDayRange();
		}
		return null;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}
