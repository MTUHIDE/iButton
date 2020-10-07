package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import app.IButtonApp;
import network.CoCoTempURLs;

/**
 * The JPanel for the settings GUI. This JPanel displays basic information about
 * the CoCoiTemp. Such as, the user Guide location and the CoCo Temp site.
 * 
 * @author Justin Havely
 */
public class About extends GUI{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates and adds all of the components to this JPanel.
	 */
	public About() throws IOException {
		super("About");
		setBackground(new Color(0,0,205));

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);

		//Image
		BufferedImage myPicture = ImageIO.read(new File("res/logo.png"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(picLabel, gbc_lblNewLabel);

		// About text area
		JTextArea txtAboutCoCoiTemp = new JTextArea("About: Coco iTemp version: " + IButtonApp.version + "\n"
				+ "\nThis application is for managing CoCo Temp sites that use an iButton for temperature collection."
				+ "\nThe CoCo Temp website can be found here: " + CoCoTempURLs.SITE.url()
				+ "\n\nFor help on using CoCo iTemp App see the CoCo iButton user guide in Start -> "
				+ "\nCoCoiTemp -> CoCoiButtonAppUserGuide.pdf"
				+ "\n\nThe open source code for the CoCo iTemp App can be found here:"
				+ "\nhttps://github.com/MTUHIDE/iButton");
		txtAboutCoCoiTemp.setEditable(false);
		txtAboutCoCoiTemp.setBackground(new Color(0, 0, 205));
		txtAboutCoCoiTemp.setForeground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.fill = GridBagConstraints.VERTICAL;
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 2;
		add(txtAboutCoCoiTemp, gbc_textArea);

		// Back button
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		add(btnBack, gbc_btnNewButton);
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
