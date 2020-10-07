package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import app.IButtonApp;
import network.CoCoTempURLs;
import network.LoginController;
import output.Logger;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;

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
	public Login() throws IOException {
	    super("Login");
		setBackground(new Color(0, 0, 205));

		// Create Layout
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);

		//Get image
		BufferedImage myPicture = ImageIO.read(new File("res/logo.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		GridBagConstraints gbc_lblPicture = new GridBagConstraints();
		gbc_lblPicture.insets = new Insets(0, 0, 5, 5);
		gbc_lblPicture.gridx = 1;
		gbc_lblPicture.gridy = 0;
		add(picLabel, gbc_lblPicture);


		// labels for the fields

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		lblUsername.setForeground(Color.LIGHT_GRAY);
		lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.SOUTH;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 1;
		add(lblUsername, gbc_lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 13));
		lblPassword.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 1;
		gbc_lblPassword.gridy = 3;
		add(lblPassword, gbc_lblPassword);

//		JLabel lblVersion = new JLabel("CoCo iTemp v" + IButtonApp.version);
//		lblVersion.setBounds(730, 429, 137, 14);
//		add(lblVersion);

		// Username field
		username = new JTextField();
		GridBagConstraints gbc_txtUsername = new GridBagConstraints();
		gbc_txtUsername.insets = new Insets(0, 0, 5, 5);
		gbc_txtUsername.gridx = 1;
		gbc_txtUsername.gridy = 2;
		add(username, gbc_txtUsername);
		username.setColumns(10);

		// Password field
		passwordField = new JPasswordField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 4;
		add(passwordField, gbc_textField_1);
		passwordField.setColumns(10);

		//Member text
		JLabel lblRegister = new JLabel("Not a member?");
		lblRegister.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		lblRegister.setVerticalAlignment(SwingConstants.TOP);
		lblRegister.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_lblRegister1 = new GridBagConstraints();
		gbc_lblRegister1.anchor = GridBagConstraints.SOUTH;
		gbc_lblRegister1.insets = new Insets(0, 0, 0, 5);
		gbc_lblRegister1.gridx = 2;
		gbc_lblRegister1.gridy = 8;
		add(lblRegister, gbc_lblRegister1);

		// Login button
//		btnLogin = new JButton("Login");
//		btnLogin.setBounds(380, 242, 89, 23);
//		btnLogin.addActionListener(this);
//		btnLogin.setBackground(Color.LIGHT_GRAY);
//		add(btnLogin);
		JButton btnLogin = new JButton("Login");
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 6;
		btnLogin.addActionListener(this);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		add(btnLogin, gbc_btnLogin);

		// Register button
//		btnRegister = new JButton("Register");
//		btnRegister.setBounds(380, 276, 89, 23);
//		btnRegister.addActionListener(this);
//		btnRegister.setBackground(Color.LIGHT_GRAY);
//		add(btnRegister);
		btnRegister = new JButton("Register");
		GridBagConstraints gbc_btnRegister = new GridBagConstraints();
		gbc_btnRegister.anchor =  GridBagConstraints.SOUTH;
		gbc_btnRegister.gridx = 3;
		gbc_btnRegister.gridy = 8;
		btnRegister.addActionListener(this);
		btnRegister.setBackground(Color.LIGHT_GRAY);
		add(btnRegister, gbc_btnRegister);

		// Settings button
//		btnAbout = new JButton("About");
//		btnAbout.setBounds(10, 420, 89, 23);
//		btnAbout.addActionListener(this);
//		btnAbout.setBackground(Color.LIGHT_GRAY);
//		add(btnAbout);
		btnAbout = new JButton("About");
		GridBagConstraints gbc_btnAbout = new GridBagConstraints();
		gbc_btnAbout.anchor = GridBagConstraints.SOUTH;
		gbc_btnAbout.insets = new Insets(0, 0, 0, 5);
		gbc_btnAbout.gridx = 0;
		gbc_btnAbout.gridy = 8;
		btnAbout.addActionListener(this);
		btnAbout.setBackground(Color.LIGHT_GRAY);
		add(btnAbout, gbc_btnAbout);


		// Wrong pass or username text
		lblWrongCons = new JLabel("Wrong Username or Password");
		lblWrongCons.setEnabled(false);
		lblWrongCons.setVisible(false);
		lblWrongCons.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWrongCons.setForeground(Color.RED);
		GridBagConstraints gbc_lblWrong = new GridBagConstraints();
		gbc_lblWrong.insets = new Insets(0, 0, 5, 5);
		gbc_lblWrong.gridx = 1;
		gbc_lblWrong.gridy = 7;
		add(lblWrongCons, gbc_lblWrong);
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
