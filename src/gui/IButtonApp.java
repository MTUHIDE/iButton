package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dalsemi.onewire.OneWireException;
import com.google.gson.Gson;

import handlers.DeviceHandler;
import network.Authentication;
import network.Site;
import output.Logger;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public static final float version = 0.05f;

	private static JPanel cards = new JPanel();
	private static Login login = new Login();
	private static gui.Site site = new gui.Site();
	private static Settings settings = new Settings();
	private static DashBoard dashboard = new DashBoard();
	private static CardLayout cardLayout;
	private static String previousCard, currentCard;
	
	public static String user, pass;

	public IButtonApp() {
		cards.setLayout(new CardLayout());
		cards.add(login, "Login");
		cards.add(dashboard, "Dashboard");
		cards.add(site, "Add Site");
		cards.add(settings, "Settings");
		cardLayout = (CardLayout) cards.getLayout();
		
		cardLayout.show(cards, "Login");
		currentCard = "Login";
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(854, 480));
		
		add(cards);
		pack();
		
		setVisible(true);
		setResizable(false);
	}

	public static void main(String[] args) {
		new IButtonApp();
	}

	public static List<DeviceHandler> getApaters() {
		try {
			Logger.writeToLog("Found adapters");
			return DeviceHandler.getDevices(DeviceHandler.deviceDefaultName, DeviceHandler.adapterDefaultName);
		} catch (OneWireException e) {
			Logger.writeErrorToLog(e);
		}
		return null;
	}

	public static Site[] getSites(String name, String pass) throws IOException {
		InputStream response = Authentication.authentication(name, pass, new URL(Authentication.SITES_URL));
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));

		Gson gson = new Gson();
		Site[] sites = gson.fromJson(reader, Site[].class);
		reader.close();

		return sites;
	}

	public static boolean login(String name, String password) {
		user = name;
		pass = password;
		try {
			Authentication.authentication(name, password, new URL(Authentication.SITES_URL));
			dashboard.setSites(getSites(name, password));
			dashboard.createAndShowGUI();
			showCard("Dashboard");
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	public static void showCard(String cardName) {
		previousCard = currentCard;
		currentCard = cardName;
		cardLayout.show(cards, cardName);
	}

	public static void showPreviousCard() {
		showCard(previousCard);
	}

}
