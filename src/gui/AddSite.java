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

public class AddSite extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextField siteName, lat, lon;
	private JTextArea description;
	private JComboBox<DeviceHandler> devices;

	public AddSite() {
		setBackground(Color.WHITE);
		setLayout(null);

		siteName = new JTextField();
		siteName.setBounds(296, 61, 261, 20);
		siteName.setColumns(10);
		add(siteName);

		lat = new JTextField();
		lat.setBounds(296, 92, 86, 20);
		lat.setColumns(10);
		add(lat);

		lon = new JTextField();
		lon.setBounds(471, 92, 86, 20);
		lon.setColumns(10);
		add(lon);

		description = new JTextArea();
		description.setBackground(Color.LIGHT_GRAY);
		description.setBounds(296, 123, 261, 85);
		add(description);

		JLabel lblSiteName = new JLabel("Site Name");
		lblSiteName.setBounds(211, 64, 64, 14);
		add(lblSiteName);

		JLabel lblLat = new JLabel("Latitude");
		lblLat.setBounds(211, 95, 46, 14);
		add(lblLat);

		JLabel lblLon = new JLabel("Longitude");
		lblLon.setBounds(405, 95, 69, 14);
		add(lblLon);

		JLabel lblDesrcpiton = new JLabel("Description");
		lblDesrcpiton.setBounds(211, 128, 75, 14);
		add(lblDesrcpiton);

		JLabel lblDevice = new JLabel("Device");
		lblDevice.setBounds(211, 222, 46, 14);
		add(lblDevice);

		devices = new JComboBox<DeviceHandler>();
		for (DeviceHandler d : IButtonApp.getApaters()) {
			devices.addItem(d);
		}
		devices.setBounds(296, 219, 86, 20);
		add(devices);

		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.LIGHT_GRAY);
		btnAdd.addActionListener(this);
		btnAdd.setBounds(656, 412, 89, 23);
		add(btnAdd);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		btnBack.setBounds(755, 412, 89, 23);
		add(btnBack);

	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}
		if (action.getActionCommand() == "Add") {
			//network.Site.newSite(siteName.getText(), Double.parseDouble(lat.getText()),
			//		Double.parseDouble(lon.getText()), description.getText());
			devices.getSelectedItem();
		}
	}
}
