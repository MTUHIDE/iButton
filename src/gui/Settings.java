package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class Settings extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Settings() {
		setBackground(Color.WHITE);
		setLayout(null);

		// TODO Update About
		JTextArea txtrAboutCocoIbutton = new JTextArea("About: Coco iButton App v0.07\r\n\r\r\nThis application is for managing CoCoTemp sites that are connected to an iButton device (DS9094). \r\r\nMore information on CoCoTemp can be found here: Site Here. \r\r\nFor help on using CoCo iButton App go to: Site Name Here \r\r\nEmail problems to: Email Here");
		txtrAboutCocoIbutton.setEditable(false);
		txtrAboutCocoIbutton.setBounds(24, 21, 815, 146);
		add(txtrAboutCocoIbutton);

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
