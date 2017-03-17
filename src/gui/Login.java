package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.SwingConstants;

import output.Logger;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Login extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField username;
	private JPasswordField passwordField;
	private JButton btnLogin, btnRegister, btnSettings;
	private JLabel lblWrongCons;

	public Login() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(393, 94, 64, 14);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblWelcome);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(275, 138, 64, 14);
		lblUsername.setLabelFor(username);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(275, 187, 79, 14);
		lblPassword.setLabelFor(passwordField);
		add(lblPassword);

		JLabel lblversion = new JLabel("CoCo iButton App v" + IButtonApp.version);
		lblversion.setBounds(707, 429, 137, 14);
		add(lblversion);

		username = new JTextField();
		username.setBounds(275, 153, 300, 23);
		add(username);
		username.setColumns(25);

		passwordField = new JPasswordField();
		passwordField.setBounds(275, 204, 300, 23);
		add(passwordField);

		btnLogin = new JButton("Login");
		btnLogin.setBounds(380, 242, 89, 23);
		btnLogin.addActionListener(this);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		add(btnLogin);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(380, 276, 89, 23);
		btnRegister.addActionListener(this);
		btnRegister.setBackground(Color.LIGHT_GRAY);
		add(btnRegister);

		btnSettings = new JButton("Settings");
		btnSettings.setBounds(10, 420, 89, 23);
		btnSettings.addActionListener(this);
		btnSettings.setBackground(Color.LIGHT_GRAY);
		add(btnSettings);

		lblWrongCons = new JLabel("Wrong Password or Username");
		lblWrongCons.setEnabled(false);
		lblWrongCons.setVisible(false);
		lblWrongCons.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWrongCons.setHorizontalAlignment(SwingConstants.CENTER);
		lblWrongCons.setForeground(Color.RED);
		lblWrongCons.setBounds(328, 310, 198, 14);
		add(lblWrongCons);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Login") {
			if (!IButtonApp.login(username.getText(), new String(passwordField.getPassword()))) {
				lblWrongCons.setEnabled(true);
				lblWrongCons.setVisible(true);
			} else {
				Logger.writeToLog("User: " + username.getText() + " connected");
				lblWrongCons.setEnabled(false);
				lblWrongCons.setVisible(false);
			}
		}
		if (action.getActionCommand() == "Register") {
			if (Desktop.isDesktopSupported()) {
				try {
					Desktop.getDesktop().browse(new URI("https://cocotemp.herokuapp.com/register"));
				} catch (IOException | URISyntaxException e) {
					Logger.writeErrorToLog(e);
				}
			}
		}
		if (action.getActionCommand() == "Settings") {
			IButtonApp.showCard("Settings");
		}
	}
}
