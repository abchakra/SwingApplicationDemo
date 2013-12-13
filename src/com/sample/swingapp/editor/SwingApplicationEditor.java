package com.sample.swingapp.editor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
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
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;

import com.sample.swingapp.jaxb.io.StockCode;
import com.sample.swingapp.jaxb.io.StockCodeIO;
import com.sample.swingapp.jaxb.io.StockCodes;
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
	private static JTable stockTable;

	public final static String accountID = "ABABCHAK2007";
	private static List<StockCode> stockCodeList = new ArrayList<StockCode>();
	private static List<StockBean> stockBeanList = new ArrayList<StockBean>();
	private static JLabel statusLabel;

	public JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu;

		// Create the menu bar.
		menuBar = new JMenuBar();

		// Build the first menu.
		menu = new JMenu("Stocks");
		menu.setMnemonic(KeyEvent.VK_S);
		menu.getAccessibleContext().setAccessibleDescription(
				"The menu contains all the option for Stocks");
		menuBar.add(menu);

		// a group of JMenuItems
		addStockMenuItem = new JMenuItem("Add Stock", KeyEvent.VK_A);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		addStockMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				ActionEvent.ALT_MASK));
		addStockMenuItem.getAccessibleContext().setAccessibleDescription(
				"Add Stock to you profile");
		addStockMenuItem.addActionListener(this);

		saveStockMenuItem = new JMenuItem("Save Stock", KeyEvent.VK_S);
		// menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
		saveStockMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				ActionEvent.ALT_MASK));
		saveStockMenuItem.getAccessibleContext().setAccessibleDescription(
				"Save the Stocks in User File");
		saveStockMenuItem.addActionListener(this);

		menu.add(addStockMenuItem);
		menu.add(saveStockMenuItem);

		// ImageIcon icon = createImageIcon("images/middle.gif");
		// menuItem = new JMenuItem("Both text and icon", icon);
		// menuItem.setMnemonic(KeyEvent.VK_B);
		// menu.add(menuItem);

		return menuBar;
	}

	public Container createContentPane() {
		// Create the content-pane-to-be.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);

		tablePanel = createStockTable();
		// Create the scroll pane and add the table to it.
		scrollPane = new JScrollPane(tablePanel);
		// Add the scroll pane to this panel.
		contentPane.add(scrollPane, BorderLayout.CENTER);

		return contentPane;
	}

	private JTable createStockTable() {

		stockTable = new JTable(new StockTableModel(stockBeanList));
		stockTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
		stockTable.setFillsViewportHeight(true);

		// if (DEBUG) {
		// table.addMouseListener(new MouseAdapter() {
		// public void mouseClicked(MouseEvent e) {
		// printDebugData(table);
		// }
		// });
		// }

		return stockTable;

	}

	private List<StockBean> getStockBean() {
		return null;
	}

	/**
	 * Returns an ImageIcon, or null if the path was invalid.
	 * 
	 * @param stockCode
	 */
	// protected static ImageIcon createImageIcon(String path) {
	// java.net.URL imgURL = MenuLookDemo.class.getResource(path);
	// if (imgURL != null) {
	// return new ImageIcon(imgURL);
	// } else {
	// System.err.println("Couldn't find file: " + path);
	// return null;
	// }
	// }

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
		mainFrame.setSize(450, 260);
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
			showStockPrompt();
		} else if (event.getSource() == saveStockMenuItem) {
			saveStockCodes();
		}

	}

	private void showStockPrompt() {
		final String stockCode = (String) JOptionPane.showInputDialog(null,
				"Enter the Code for Stock :");
		if (stockCode != null) {
			statusLabel.setText("Loading....!");

			new SwingWorker<Void, String>() {
				StockBean stockBean = null;

				@Override
				protected Void doInBackground() throws Exception {
					// Worken hard or hardly worken...
					stockBean = StockTickerDAO.getInstance().getStockPrice(
							stockCode);
					if (stockBean != null) {
						stockBeanList.add(stockBean);
						StockCode stockCodeObj = new StockCode();
						stockCodeObj.setAccountID(accountID);
						stockCodeObj.setStockID(stockCode);
						stockCodeList.add(stockCodeObj);
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

		}
	}

	public static List<StockCode> loadStockCodes() {
		statusLabel.setText("Loading....!");
		new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() throws Exception {
				// Worken hard or hardly worken...

				stockCodeList = StockCodeIO.getInstance().StockCodeXMLToJava()
						.getStockCodeList();

				for (StockCode stockCode : stockCodeList) {
					StockBean stockBean = StockTickerDAO.getInstance()
							.getStockPrice(stockCode.getStockID());
					if (stockBean != null) {
						stockBeanList.add(stockBean);
					}
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

		return stockCodeList;

	}

	public void saveStockCodes() {
		StockCodes stockCodes = new StockCodes();
		stockCodes.setStockCodeID("ABABCHAK2013");
		stockCodes.setStockCodeList(stockCodeList);
		StockCodeIO.getInstance().StockCodeJavaToXML(stockCodes);
	}

	private static void setProxySetting() {
		System.setProperty("java.net.useSystemProxies", "true");
		System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
		System.setProperty("http.proxyPort", "80");
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
