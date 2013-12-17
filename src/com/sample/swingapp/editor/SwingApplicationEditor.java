package com.sample.swingapp.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import com.sample.swingapp.controls.SearchStockDialog;
import com.sample.swingapp.controls.StockDetailPanel;
import com.sample.swingapp.controls.UserAuthenticationDialog;
import com.sample.swingapp.jaxb.io.StockCode;
import com.sample.swingapp.jaxb.io.StockCodeIO;
import com.sample.swingapp.jaxb.io.StockCodes;
import com.sample.swingapp.model.StockTableModel;
import com.sample.swingapp.yahoofinance.StockBean;
import com.sample.swingapp.yahoofinance.StockTickerDAO;

/*
 * This is the Main class for Swing Application Editor
 */
public class SwingApplicationEditor implements ActionListener {
	private static JFrame mainFrame;
	private JScrollPane scrollPane;
	private JTable tablePanel;
	private JMenuItem addStockMenuItem;
	private JMenuItem saveStockMenuItem;
	private JMenuItem updateStockMenuItem;
	private StockDetailPanel stockDetailPanel;
	private JMenuItem loginMenuItem;
	private static JTable stockTable;

	public final static String accountID = "ABABCHAK2007";

	private static HashMap<String, StockCode> stockCodes = new HashMap<String, StockCode>();
	private static List<StockBean> stockBeans = new ArrayList<StockBean>();
	private static JLabel statusLabel;

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// ***********************STOCKS MENU***************
		// Build the first menu.
		menu = new JMenu("Stocks");
		menu.setMnemonic(KeyEvent.VK_S);
		menu.getAccessibleContext().setAccessibleDescription(
				"The menu contains all the option for Stocks");

		// a group of JMenuItems
		addStockMenuItem = new JMenuItem("Add Stock", KeyEvent.VK_A);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		addStockMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
				ActionEvent.ALT_MASK));
		addStockMenuItem.getAccessibleContext().setAccessibleDescription(
				"Add Stock to you profile");
		addStockMenuItem.addActionListener(this);

		saveStockMenuItem = new JMenuItem("Save Stock", KeyEvent.VK_S);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		saveStockMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6,
				ActionEvent.ALT_MASK));
		saveStockMenuItem.getAccessibleContext().setAccessibleDescription(
				"Save the Stocks in User File");
		saveStockMenuItem.addActionListener(this);

		updateStockMenuItem = new JMenuItem("Update Stock", KeyEvent.VK_U);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		updateStockMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_7, ActionEvent.ALT_MASK));
		updateStockMenuItem.getAccessibleContext().setAccessibleDescription(
				"Update the Stocks in User File");
		updateStockMenuItem.addActionListener(this);
		menu.add(addStockMenuItem);
		menu.add(saveStockMenuItem);
		menu.add(updateStockMenuItem);

		// ************************USER MENU*********************

		JMenu profileMenu = new JMenu("User Profile");
		profileMenu.setMnemonic(KeyEvent.VK_U);
		profileMenu.getAccessibleContext().setAccessibleDescription(
				"The menu contains all the option for User Profile");

		loginMenuItem = new JMenuItem("Login to Profile", KeyEvent.VK_L);
		loginMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		loginMenuItem.getAccessibleContext().setAccessibleDescription(
				"Login in to UserProfile");
		loginMenuItem.addActionListener(this);

		profileMenu.add(loginMenuItem);

		menuBar.add(profileMenu);
		menuBar.add(menu);
		return menuBar;
	}

	public Container createContentPane() {
		// Create the content-pane-to-be.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);

		// Do the layout.
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(splitPane, BorderLayout.CENTER);

		// JPanel topPane = new JPanel(new BorderLayout());
		// topPane.setOpaque(true);
		tablePanel = createStockTable();
		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(tablePanel);

		// Add the scroll pane to this splitPane.
		splitPane.add(scrollPane);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setOpaque(true);

		stockDetailPanel = new StockDetailPanel();
		stockDetailPanel.setVisible(false);
		// Create the scroll pane and add the table to it.
		JScrollPane bottomScrollPane = new JScrollPane(stockDetailPanel);

		splitPane.add(bottomScrollPane);

		return contentPane;
	}

	private JTable createStockTable() {
		stockTable = new JTable(new StockTableModel(stockBeans));
		stockTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		stockTable.setFillsViewportHeight(true);
		stockTable
				.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		stockTable.getSelectionModel().addListSelectionListener(
				new StockTableRowListener());
		return stockTable;

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window. used as a Toplevel Container Class
		mainFrame = new JFrame("SwingApplicationEditor");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		SwingApplicationEditor appEditor = new SwingApplicationEditor();
		mainFrame.setJMenuBar(appEditor.createMenuBar());
		mainFrame.setContentPane(appEditor.createContentPane());

		// Display the window.
		// mainFrame.setSize(800, 600);
		mainFrame.setExtendedState(mainFrame.getExtendedState()
				| JFrame.MAXIMIZED_BOTH);
		mainFrame.setVisible(true);

		// create the status bar panel and shove it down the bottom of the frame
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		mainFrame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel = new JLabel("status");
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		setProxySetting();
		loadStockCodes();

	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == addStockMenuItem) {
			// try {
			// JSONParserUtil.searchStockCodeByCompanyName("Goo");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			showStockPrompt();
		} else if (event.getSource() == saveStockMenuItem) {
			saveStockCodes();
		} else if (event.getSource() == updateStockMenuItem) {
			updateStockCodes();
		}
		if (event.getSource() == loginMenuItem) {
			showLoginPrompt();
		}

	}

	private void showLoginPrompt() {
		UserAuthenticationDialog userDialog = new UserAuthenticationDialog(
				mainFrame, true, "Login to User Profile");

	}

	private void updateStockCodes() {
		statusLabel.setText("Loading....!");
		new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {

				StringBuilder symbols = new StringBuilder("");
				for (StockBean stockBean : stockBeans) {
					symbols.append("+" + stockBean.getTicker());
				}
				String symbols_temp = symbols.toString();
				StockTickerDAO.getInstance().refresStockBeans(stockBeans,
						symbols_temp.substring(1, symbols_temp.length()));

				((AbstractTableModel) stockTable.getModel())
						.fireTableDataChanged();
				return null;
			}

			@Override
			protected void done() {
				statusLabel.setText("All Stocks Re-Loaded !!!");

			}
		}.execute();

	}

	private void showStockPrompt() {
		// final String stockCode = (String) JOptionPane.showInputDialog(null,
		// "Enter the Code for Stock :");

		SearchStockDialog searchDialog = new SearchStockDialog(mainFrame, true,
				"Find Stock Symbols");
		final String stockCode = "";
		if (stockCode != null && stockCode.length()>0) {

			if (!stockCodes.containsKey(stockCode)) {
				statusLabel.setText("Loading....!");

				new SwingWorker<Void, String>() {
					StockBean stockBean = null;

					@Override
					protected Void doInBackground() throws Exception {
						stockBean = StockTickerDAO.getInstance().getStockPrice(
								stockCode);
						if (stockBean != null) {
							stockBeans.add(stockBean);
							StockCode stockCodeObj = new StockCode();
							stockCodeObj.setAccountID(accountID);
							stockCodeObj.setStockID(stockCode);
							stockCodes.put(stockCode, stockCodeObj);
							((AbstractTableModel) stockTable.getModel())
									.fireTableDataChanged();
						}
						return null;
					}

					@Override
					protected void done() {
						if (stockBean != null) {
							statusLabel.setText("Stock Price of " + stockCode
									+ "  is :" + stockBean.getPrice());
						} else {
							statusLabel.setText("Stock Code is WRONG !!!");
						}

					}
				}.execute();
			} else {
				JOptionPane.showMessageDialog(null,
						"Stock is already added in your Profile !");
			}
		}
	}

	public static void loadStockCodes() {
		statusLabel.setText("Loading....!");
		new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				// Worken hard or hardly worken...

				List<StockCode> stockCodeList = StockCodeIO.getInstance()
						.StockCodeXMLToJava().getStockCodeList();
				StringBuilder symbols = new StringBuilder("");
				for (StockCode stockCode : stockCodeList) {
					stockCodes.put(stockCode.getStockID(), stockCode);
					symbols.append("+" + stockCode.getStockID());

				}

				List<StockBean> stockBeanListTemp = StockTickerDAO
						.getInstance().getStockBeans(symbols.toString());
				for (StockBean stockBean : stockBeanListTemp) {
					stockBeans.add(stockBean);
				}

				((AbstractTableModel) stockTable.getModel())
						.fireTableDataChanged();
				return null;
			}

			@Override
			protected void done() {
				statusLabel.setText("All Stocks Loaded !!!");

			}
		}.execute();

	}

	public void saveStockCodes() {
		StockCodes stockCodesJAXB = new StockCodes();
		stockCodesJAXB.setStockCodeID("ABABCHAK2013");

		List<StockCode> stockCodeList = new ArrayList<StockCode>();
		stockCodeList.addAll(stockCodes.values());
		stockCodesJAXB.setStockCodeList(stockCodeList);
		StockCodeIO.getInstance().StockCodeJavaToXML(stockCodesJAXB);
	}

	private static void setProxySetting() {
		System.setProperty("java.net.useSystemProxies", "true");
		System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
		System.setProperty("http.proxyPort", "80");
	}

	private class StockTableRowListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
				return;
			}
			final StockBean selectionStockBean = stockBeans.get(stockTable
					.getSelectionModel().getLeadSelectionIndex());
			if (selectionStockBean != null) {
				final String selectedTicker = selectionStockBean.getTicker();
				statusLabel.setText("Loading details for " + selectedTicker
						+ ".....!");
				new SwingWorker<Void, String>() {

					@Override
					protected Void doInBackground() throws Exception {

						StockTickerDAO.getInstance().loadAllForStockBean(
								selectedTicker);
						stockDetailPanel.setVisible(true);
						stockDetailPanel.setStockBean(selectionStockBean);
						stockDetailPanel.refreshView();
						return null;
					}

					@Override
					protected void done() {
						statusLabel.setText(selectedTicker + " Loaded !!!");
					}
				}.execute();

			}
		}
	}

	// private void checkInternetStatus() {
	//
	// URL u;
	// try {
	// u = new URL("http://www.google.com/");
	//
	// System.out
	// .println("Checking internet connection availability.....");
	//
	// HttpURLConnection uc = (HttpURLConnection) u.openConnection();
	// uc.setReadTimeout(5000);
	// uc.setConnectTimeout(5000);
	// System.out.println("Response code : " + uc.getResponseCode());
	// System.out.println("Internet connection is available.....");
	// } catch (MalformedURLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

}
