package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dalsemi.onewire.OneWireException;
import handlers.DeviceHandler;
import network.Site;
import output.Logger;
import output.SiteData;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final float version = 0.07f;

	private static JPanel cards = new JPanel();
	private static Login login = new Login();
	private static AddSite Addsite = new AddSite();
	public static EditSite editSite = new EditSite();
	private static Settings settings = new Settings();
	private static DashBoard dashboard = new DashBoard();
	private static CardLayout cardLayout;
	private static String previousCard, currentCard;

	public static String user, pass;

	public IButtonApp() {
		cards.setLayout(new CardLayout());
		cards.add(login, "Login");
		cards.add(dashboard, "Dashboard");
		cards.add(Addsite, "Add Site");
		cards.add(editSite, "Edit Site");
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

	public static boolean login(String name, String password) {
		user = name;
		pass = password;
		try {

			Site[] server = Site.getSites(name, password);
			List<DeviceHandler> devices = getApaters();

			for (Site s : server) {
				if (SiteData.findSite(s.id) == null) {
					SiteData.addSite(s.id, "null");
				} else {
					for (DeviceHandler d : devices) {
						if (SiteData.findSite(s.id)[1].equals(d.getAddress())) {
							s.device = d;
							break;
						}
					}
				}

			}

			dashboard.setSites(server);

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
