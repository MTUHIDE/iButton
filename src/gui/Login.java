package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.SwingConstants;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Login extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextField username;
	private JPasswordField passwordField;
	private JButton btnLogin, btnRegister, btnSettings;

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

	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == btnLogin) {
			IButtonApp.login();
			try {
				IButtonApp.getSites();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (action.getSource() == btnRegister) {
			System.out.println("Register");
		}
	}
}
