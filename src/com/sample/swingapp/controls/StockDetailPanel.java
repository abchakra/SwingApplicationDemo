package com.sample.swingapp.controls;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.sample.swingapp.model.StockDetailTableModel;
import com.sample.swingapp.yahoofinance.StockBean;

public class StockDetailPanel extends JPanel {

	private JTable stockDetailTable;

	private JLabel imageLabel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StockDetailPanel() {
		setLayout(new BorderLayout());
		stockDetailTable = new JTable(new StockDetailTableModel());
		stockDetailTable.setPreferredScrollableViewportSize(new Dimension(500,
				70));
		stockDetailTable.setFillsViewportHeight(true);

		imageLabel = new JLabel();

		JScrollPane scrollPane = new JScrollPane(stockDetailTable);
		add(scrollPane, BorderLayout.CENTER);
		add(imageLabel, BorderLayout.LINE_END);
	}

	public void setStockBean(StockBean stockBean) {
		StockDetailTableModel tableModel = (StockDetailTableModel) stockDetailTable
				.getModel();
		tableModel.setStockBean(stockBean);

		imageLabel.setIcon(StockChartURLImage.getStockChartURLImage(stockBean
				.getTicker()));
		imageLabel.repaint();

		updateUI();
	}

	public void refreshView() {
		((AbstractTableModel) stockDetailTable.getModel())
				.fireTableDataChanged();
	}
}
