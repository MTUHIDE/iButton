package gui;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.SwingConstants;

import app.IButtonApp;
import network.CoCoTempURLs;
import network.LoginController;
import output.Logger;

import javax.swing.JButton;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * The JPanel for the login GUI. This JPanel displays the login fields.
 * 
 * @author Justin Havely
 *
 */
public class Login extends GUI {
	private static final long serialVersionUID = 1L;

	private JTextField username;
	private JPasswordField passwordField;
	private JButton btnLogin, btnRegister, btnAbout;
	private JLabel lblWrongCons;

	/**
	 * Creates and adds all of the components to this JPanel.
	 */
	public Login() {
	    super("Login");
		setBackground(Color.WHITE);
		setLayout(null);

		// labels for the fields
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

		JLabel lblVersion = new JLabel("CoCo iTemp v" + IButtonApp.version);
		lblVersion.setBounds(730, 429, 137, 14);
		add(lblVersion);

		// Username field
		username = new JTextField();
		username.setBounds(275, 153, 300, 23);
        username.setColumns(25);
		add(username);

		// Password field
		passwordField = new JPasswordField();
		passwordField.setBounds(275, 204, 300, 23);
		add(passwordField);

		// Login button
		btnLogin = new JButton("Login");
		btnLogin.setBounds(380, 242, 89, 23);
		btnLogin.addActionListener(this);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		add(btnLogin);

		// Register button
		btnRegister = new JButton("Register");
		btnRegister.setBounds(380, 276, 89, 23);
		btnRegister.addActionListener(this);
		btnRegister.setBackground(Color.LIGHT_GRAY);
		add(btnRegister);

		// Settings button
		btnAbout = new JButton("About");
		btnAbout.setBounds(10, 420, 89, 23);
		btnAbout.addActionListener(this);
		btnAbout.setBackground(Color.LIGHT_GRAY);
		add(btnAbout);

		// Wrong pass or username text
		lblWrongCons = new JLabel("Wrong Password or Username");
		lblWrongCons.setEnabled(false);
		lblWrongCons.setVisible(false);
		lblWrongCons.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWrongCons.setHorizontalAlignment(SwingConstants.CENTER);
		lblWrongCons.setForeground(Color.RED);
		lblWrongCons.setBounds(328, 310, 198, 14);
		add(lblWrongCons);
	}

	/**
	 * Listens for mouse clicks and contain the logic for logging in.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Login")) {
			if (!LoginController.login(username.getText(), new String(passwordField.getPassword()))) {
				// Login failed
				lblWrongCons.setEnabled(true);
				lblWrongCons.setVisible(true);
			} else {
				// Login worked
				Logger.writeToLog("User: " + username.getText() + " connected");
				//Shows and updates the dashboard
				IButtonApp.showCard(GUI.dashboard);
                //Hides the wrong password text
				lblWrongCons.setEnabled(false);
				lblWrongCons.setVisible(false);
			}
		}
		// opens up a Internet browser to the registration page
		if (action.getActionCommand().equals("Register")) {
			openRegisterInBrowser();
		}
		// Shows the settings JPanel
		if (action.getActionCommand().equals("About")) {
			IButtonApp.showCard(GUI.about);
		}
	}

	/**
	 * Opens the registration page in a Internet browser.
	 * 
	 * @return True if the page was open, false if not.
	 */
	private boolean openRegisterInBrowser() {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(CoCoTempURLs.REGISTER.url()));
				return true;
			} catch (IOException | URISyntaxException e) {
				Logger.writeErrorToLog(e);
				return false;
			}
		}
		return false;
	}

}
