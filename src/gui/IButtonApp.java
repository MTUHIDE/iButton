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

/**
 * The main CoCo iTemp App.
 * 
 * @author Justin Havely
 *
 */
public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final String version = "0.9.4";
	public static final ImageIcon img = new ImageIcon(IButtonApp.class.getResource("/iT_icon.jpg"));

	private static JPanel cards = new JPanel();
	private static CardLayout cardLayout;
	private static String previousCard, currentCard;

	public static Login login = new Login();
	public static AddSite Addsite = new AddSite();
	public static EditSite editSite = new EditSite();
	public static Settings settings = new Settings();
	public static DashBoard dashboard = new DashBoard();

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

	/**
	 * Gets all of the iButton devices connected through USB.
	 * 
	 * @return Null if no devices are found. Else, a list of all found devices
	 *         will be return.
	 */
	public static List<DeviceHandler> getDevices() {
		try {
			return DeviceHandler.getDevices(DeviceHandler.deviceDefaultName, DeviceHandler.adapterDefaultName);
		} catch (OneWireException e) {
			Logger.writeErrorToLog(e);
		}
		return null;
	}

	/**
	 * Loads the sites from the server, updates the local siteData file with new
	 * sites, assigns device to sites, and updates the dashboard.
	 * 
	 * @param name
	 *            Username of the user
	 * @param password
	 *            Password of the user
	 * @throws IOException
	 *             If username and password are not correct.
	 */
	public static void loadSites(String name, String password) throws IOException {
		// Get sites from server.
		Site[] serverSites = Site.getSites(name, password);
		List<DeviceHandler> devices = getDevices();

		// Wrong username or password.
		if (serverSites == null) {
			throw new IOException();
		}

		for (Site s : serverSites) {
			// Checks if site is not in siteData file.
			if (SiteData.findSite(s.id) == null) {
				SiteData.addSite(s.id, "null");
			} else {
				for (DeviceHandler d : devices) {
					// Checks if that site has a device assign to it.
					if (SiteData.findSite(s.id)[1].equals(d.getAddress())) {
						s.device = d;
						break;
					}
				}
			}

		}

		dashboard.updateSiteList(serverSites);
	}

	/**
	 * Shows a new JPanel.
	 * 
	 * @param cardName
	 *            The JPanel to render.
	 */
	public static void showCard(String cardName) {
		previousCard = currentCard;
		currentCard = cardName;
		cardLayout.show(cards, cardName);
	}

	/**
	 * Shows the last render JPanel
	 */
	public static void showPreviousCard() {
		showCard(previousCard);
	}

}
