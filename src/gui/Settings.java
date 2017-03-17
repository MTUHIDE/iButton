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
		JTextArea textArea = new JTextArea("About: Coco iButton App v" + IButtonApp.version
				+ "\nAll your Base Belong to us!");
		textArea.setEditable(false);
		textArea.setBounds(24, 21, 416, 146);
		add(textArea);

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
