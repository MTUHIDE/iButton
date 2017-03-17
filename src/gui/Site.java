package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import handlers.DeviceHandler;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

public class Site extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox<DeviceHandler> comboBox;

	public Site() {
		setBackground(Color.WHITE);
		setLayout(null);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		btnBack.setBounds(755, 412, 89, 23);
		add(btnBack);

		textField = new JTextField();
		textField.setBounds(296, 61, 261, 20);
		add(textField);
		textField.setColumns(10);

		JLabel lblSiteName = new JLabel("Site Name");
		lblSiteName.setBounds(211, 64, 64, 14);
		add(lblSiteName);

		textField_1 = new JTextField();
		textField_1.setBounds(296, 92, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(471, 92, 86, 20);
		add(textField_2);
		textField_2.setColumns(10);

		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setBounds(296, 123, 261, 85);
		add(textArea);

		JLabel lblLat = new JLabel("Latitude");
		lblLat.setBounds(211, 95, 46, 14);
		add(lblLat);

		JLabel lblLon = new JLabel("Longitude");
		lblLon.setBounds(405, 95, 69, 14);
		add(lblLon);

		JLabel lblDesrcpiton = new JLabel("Description");
		lblDesrcpiton.setBounds(211, 128, 75, 14);
		add(lblDesrcpiton);

		comboBox = new JComboBox<DeviceHandler>();
		for (DeviceHandler d : IButtonApp.getApaters()) {
			comboBox.addItem(d);
		}
		comboBox.setBounds(296, 219, 86, 20);
		add(comboBox);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.LIGHT_GRAY);
		btnAdd.setBounds(656, 412, 89, 23);
		add(btnAdd);

		JLabel lblDevice = new JLabel("Device");
		lblDevice.setBounds(211, 222, 46, 14);
		add(lblDevice);

	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}
		if(action.getActionCommand() == "Add"){
			comboBox.getSelectedItem();
		}
	}
}
