package com.sample.swingapp.controls;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class SearchStockDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton enterButton;
	private JButton exitButton;

	public SearchStockDialog(JFrame frame, boolean modal, String myMessage) {
		super(frame, modal);

		JLabel stockName = new JLabel("Search Stock By Company Name :");

		ArrayList<String> items = new ArrayList<String>();
		Locale[] locales = Locale.getAvailableLocales();
		for (int i = 0; i < locales.length; i++) {
			String item = locales[i].getDisplayName();
			items.add(item);
		}

		JTextField stockNameText = new JTextField(30);
		UIHelper.setupAutoComplete(stockNameText, items);
		enterButton = new JButton("Enter");
		enterButton.addActionListener(this);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);

		setLayout(new GridLayout(2, 2));

		UIHelper.addComponent(this, stockName, 0, 0, 1, 1,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0,
						0, 0, 0), 0, 0, 0.0, 0.0);

		UIHelper.addComponent(this, stockNameText, 1, 0, 1, 1,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(0, 0, 0, 0), 0, 0, 0.0, 0.0);

		UIHelper.addComponent(this, enterButton, 0, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 0, 0.0, 0);
		UIHelper.addComponent(this, exitButton, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 0, 0.0, 0);
		setResizable(false);
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);

	}

	ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent actionEvent) {
			setVisible(false);
		}
	};

	@Override
	protected JRootPane createRootPane() {
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(actionListener, stroke,
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exitButton) {
			setVisible(false);
		}

	}
}
