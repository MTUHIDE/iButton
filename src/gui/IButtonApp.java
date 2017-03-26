package gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.dalsemi.onewire.OneWireException;
import handlers.DeviceHandler;
import network.Site;
import output.Logger;
import output.SiteData;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final float version = 0.08f;
	public static final ImageIcon img = new ImageIcon(IButtonApp.class.getResource("/iT_icon.jpg"));

	private static JPanel cards = new JPanel();
	public static Login login = new Login();
	public static AddSite Addsite = new AddSite();
	public static EditSite editSite = new EditSite();
	public static Settings settings = new Settings();
	public static DashBoard dashboard = new DashBoard();
	private static CardLayout cardLayout;
	private static String previousCard, currentCard;

	public static String user, pass;

	public IButtonApp() {
		super("Coco iTemp");

		cards.setLayout(new CardLayout());
		cards.add(login, "Login");
		cards.add(dashboard, "Dashboard");
		cards.add(Addsite, "Add Site");
		cards.add(editSite, "Edit Site");
		cards.add(settings, "Settings");
		cardLayout = (CardLayout) cards.getLayout();

		currentCard = "Login";
		cardLayout.show(cards, "Login");

		setIconImage(img.getImage());
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

	public static void loadSites(String name, String password) throws IOException {
		Site[] serverSites = Site.getSites(name, password);
		List<DeviceHandler> devices = getApaters();

		for (Site s : serverSites) {
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

		dashboard.updateSiteList(serverSites);
	}

	public static boolean login(String name, String password) {
		user = name;
		pass = password;
		try {
			loadSites(name, pass);
		} catch (IOException e) {
			return false;
		}
		showCard("Dashboard");
		return true;

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
