package app;

import java.awt.*;
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

	public static final String version = "1.1.1"; // Current build

	private static final ImageIcon img = new ImageIcon(IButtonApp.class.getResource("/iT_icon.jpg")); // IT Logo

	private static JPanel cards = new JPanel();
	private static CardLayout cardLayout;
	private static GUI previousCard, currentCard;

	/**
	 * Creates and adds all the JPanels to the JFrame. CoCo iTemp uses the card layout to
	 * show and hide JPanels.
	 */
	public IButtonApp() {
		super("Coco iTemp");

		// Cards (JPanels)
        cards.setLayout(new CardLayout());
        cards.add(GUI.login, GUI.login.cardName);
        cards.add(GUI.dashboard, GUI.dashboard.cardName);
        cards.add(GUI.about, GUI.about.cardName);
        cards.add(GUI.missionControl,GUI.missionControl.cardName);
		cardLayout = (CardLayout) cards.getLayout();
		// Shows the login JPanel
		currentCard = GUI.login;
		cardLayout.show(cards, GUI.login.cardName);

		// Sets the application icon and other JFrame settings
		setIconImage(img.getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		add(cards);
		setVisible(true);
		setResizable(true);
		pack();
	}

	public static void main(String[] args) {
		new IButtonApp();
	}

	/**
	 * Shows a new JPanel and calls the <code>update<code> method.
	 * 
	 * @param card
	 *            The JPanel to render.
	 */
	public static void showCard(GUI card) {
		previousCard = currentCard;
		currentCard = card;
		//Updates the JPanel ('card')
        currentCard.update();
		cardLayout.show(cards, card.cardName);
	}

	/**
	 * Shows the last rendered JPanel
	 */
	public static void showPreviousCard() {
		showCard(previousCard);
	}

}
