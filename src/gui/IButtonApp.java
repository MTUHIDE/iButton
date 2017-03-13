package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;

import network.Authentication;
import network.Site;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final float version = 0.05f;
	private static JPanel cards = new JPanel();
	private static Login login = new Login();
	private static DashBoard dashboard = new DashBoard();
	private static CardLayout cardLayout;

	public IButtonApp() {
		cards.setLayout(new CardLayout());
		cards.add(login, "Login");
		cards.add(dashboard, "Dashboard");
		cardLayout = (CardLayout) cards.getLayout();
		cardLayout.show(cards, "Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(854, 480));

		add(cards);
		pack();
		setVisible(true);
		this.setResizable(false);
	}

	public static void main(String[] args) {
		new IButtonApp();
	}

	public static Site[] getSites(String name, String pass) throws IOException {
		InputStream response = Authentication.authentication(name, pass, new URL(Authentication.SITES_URL));
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));

		Gson gson = new Gson();
		Site[] sites = gson.fromJson(reader, Site[].class);
		reader.close();

		return sites;
	}

	public static boolean login(String name, String pass) {
		try {
			Authentication.authentication(name, pass, new URL(Authentication.SITES_URL));
			dashboard.setSites(getSites(name, pass));
			dashboard.createAndShowGUI();
			cardLayout.show(cards, "Dashboard");
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public static void logout() {
		cardLayout.show(cards, "Login");
	}

}
