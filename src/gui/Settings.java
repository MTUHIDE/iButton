package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import app.IButtonApp;
import network.CoCoTempURLs;

import javax.swing.JButton;

/**
 * The JPanel for the settings GUI. This JPanel displays basic information about
 * the CoCoiTemp. Such as, the user Guide location and the CoCo Temp site.
 * 
 * @author Justin Havely
 */
public class Settings extends GUI{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates and adds all of the components to this JPanel.
	 */
	public Settings() {
		super("Settings");
		setBackground(Color.WHITE);
		setLayout(null);

		// About text area
		JTextArea txtAboutCocoiTemp = new JTextArea("About: Coco iTemp version: " + IButtonApp.version + "\n"
				+ "\nThis application is for managing CoCo Temp sites that use an iButton for temperature collection."
				+ "\nThe CoCo Temp website can be found here: " + CoCoTempURLs.SITE.url()
				+ "\n\nFor help on using CoCo iTemp App see the CoCo iButton user guide in Start -> "
				+ "\nCoCoiTemp -> CoCoiButtonAppUserGuide.pdf"
				+ "\n\nThe open source code for the CoCo iTemp App can be found here:"
				+ "\nhttps://github.com/MTUHIDE/iButton");
		txtAboutCocoiTemp.setEditable(false);
		txtAboutCocoiTemp.setBounds(24, 21, 815, 261);
		add(txtAboutCocoiTemp);

		// Back button
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		btnBack.setBounds(750, 420, 89, 23);
		add(btnBack);
	}

	/**
	 * Listens for mouse clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		// Shows the last JPanel
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}
	}
}
