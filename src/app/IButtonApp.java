package app;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.*;

import gui.*;

/**
 * The launching point for the CoCo iTemp application.
 * 
 * @author Justin Havely
 *
 */
public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public static final String version = "1.1.0"; // Current build
	public static final ImageIcon img = new ImageIcon(IButtonApp.class.getResource("/iT_icon.jpg"));

	private static JPanel cards = new JPanel();
	private static CardLayout cardLayout;
	private static GUI previousCard, currentCard;

	/**
	 * Creates and adds all the components to the JFrame. Uses the cardlayout to
	 * show and hide JPanels.
	 */
	public IButtonApp() {
		super("Coco iTemp");

		// cards (JPanels)
        cards.setLayout(new CardLayout());
        cards.add(GUI.login, GUI.login.cardName);
        cards.add(GUI.dashboard, GUI.dashboard.cardName);
        cards.add(GUI.Addsite, GUI.Addsite.cardName);
        cards.add(GUI.editSite, GUI.editSite.cardName);
        cards.add(GUI.settings, GUI.settings.cardName);
		cardLayout = (CardLayout) cards.getLayout();

		// Shows the login JPanel
		currentCard = GUI.login;
		cardLayout.show(cards, "Login");

		// Sets the application icon and other JFrame settings
		setIconImage(img.getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
	 * Shows a new JPanel.
	 * 
	 * @param card
	 *            The JPanel to render.
	 */
	public static void showCard(GUI card) {
		previousCard = currentCard;
		currentCard = card;
		card.update();
		cardLayout.show(cards, card.cardName);
	}

	/**
	 * Shows the last render JPanel
	 */
	public static void showPreviousCard() {
		showCard(previousCard);
	}

}
