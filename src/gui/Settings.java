package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import network.CoCoTempURLs;

import javax.swing.JButton;

public class Settings extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Settings() {
		setBackground(Color.WHITE);
		setLayout(null);

		// TODO Update About
		JTextArea txtAboutCocoiTemp = new JTextArea("About: Coco iTemp version: " + IButtonApp.version
				+ "\n"
				+ "\nThis application is for managing CoCo Temp sites that are connected to an iButton device."
				+ "\nThe CoCo Temp website can be found here: " + CoCoTempURLs.SITE.url()
				+ "\n\nFor help on using CoCo iTemp App go to:"
				+ "\nhttps://www.dropbox.com/s/aktgj9itxcry05i/CoCoiButtonAppUserGuide.pdf?dl=0"
				+ "\n\nEmail problems to: jmh628@nau.edu");
		txtAboutCocoiTemp.setEditable(false);
		txtAboutCocoiTemp.setBounds(24, 21, 815, 146);
		add(txtAboutCocoiTemp);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		btnBack.setBounds(750, 420, 89, 23);
		add(btnBack);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}
	}
}
