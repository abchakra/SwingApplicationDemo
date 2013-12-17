package com.sample.swingapp.controls;

import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UserAuthenticationDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextField userName;
	JPasswordField password;
	private JButton enterButton;
	private JButton exitButton;
	private boolean answer;

	public UserAuthenticationDialog(JFrame frame, boolean modal,
			String myMessage) {
		super(frame, modal);

		JLabel usernameLabel = new JLabel("UserName :");
		userName = new JTextField();

		JLabel passwordLabel = new JLabel("Password :");
		password = new JPasswordField(12);
		enterButton = new JButton("Enter");
		enterButton.addActionListener(this);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(LEADING)
								.addComponent(usernameLabel)
								.addComponent(passwordLabel))
				.addGroup(
						layout.createParallelGroup(LEADING)
								.addComponent(userName).addComponent(password))
				.addGroup(
						layout.createParallelGroup(Alignment.LEADING)
								.addComponent(enterButton)
								.addComponent(exitButton)));
		layout.linkSize(SwingConstants.HORIZONTAL, enterButton, exitButton);
		layout.setVerticalGroup(layout
				.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(usernameLabel)
								.addComponent(userName)
								.addComponent(enterButton))
				.addGroup(
						layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(passwordLabel)
								.addComponent(password)
								.addComponent(exitButton)));
		pack();
		setLocationRelativeTo(frame);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (enterButton == e.getSource()) {
			answer = true;
			setVisible(false);
		} else if (exitButton == e.getSource()) {
			answer = false;
			setVisible(false);
		}
	}
}
