package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import handlers.DeviceHandler;
import handlers.MissionHandler;
import network.Site;
import okhttp3.Response;
import output.SiteData;

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
		siteName.setToolTipText("E.g My House");
		siteName.setBounds(296, 61, 261, 20);
		siteName.setColumns(10);
		add(siteName);

		lat = new JTextField();
		lat.setToolTipText("E.g 11.22321");
		lat.setBounds(296, 92, 86, 20);
		lat.setColumns(10);
		add(lat);

		lon = new JTextField();
		lon.setToolTipText("E.g 11.22321");
		lon.setBounds(471, 92, 86, 20);
		lon.setColumns(10);
		add(lon);

		description = new JTextArea();
		description.setToolTipText("The location of the device.");
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
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
		devices.setBounds(296, 219, 125, 20);
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
		if (action.getActionCommand() == "Add") { //TODO Clean up
			Response response = Site.newSite(siteName.getText(), lat.getText(), lon.getText(), description.getText());
			String siteID = response.request().url().queryParameterValue(0);
			DeviceHandler device = devices.getItemAt(devices.getSelectedIndex());
			if (device != null) {
				MissionContainer ms = (MissionContainer) device.device;
				SiteData.updateSite(siteID, device.getAddress());
				try {
					MissionHandler.startMission(device.adapter, ms);
				} catch (OneWireException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				SiteData.updateSite(siteID, "null");
			}

			try {
				IButtonApp.loadSites(IButtonApp.user, IButtonApp.pass);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			IButtonApp.showCard("Dashboard");
		}
	}
	
}
