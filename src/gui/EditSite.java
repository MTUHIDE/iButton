package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import handlers.DeviceHandler;
import handlers.MissionHandler;
import network.Site;
import output.SiteData;
import java.awt.Font;

public class EditSite extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextField siteName, lat, lon;
	private JTextArea description;
	private JComboBox<DeviceHandler> devices;
	private Site site;

	public EditSite() {

		setBackground(Color.WHITE);
		setLayout(null);

		siteName = new JTextField();
		siteName.setToolTipText("E.g My House");
		siteName.setBounds(296, 61, 261, 20);
		siteName.setColumns(10);
		add(siteName);

		lat = new JTextField();
		lat.setToolTipText("E.g 11.22321");
		lat.setBounds(296, 92, 261, 20);
		lat.setColumns(10);
		add(lat);

		lon = new JTextField();
		lon.setToolTipText("E.g 11.22321");
		lon.setBounds(296, 123, 261, 20);
		lon.setColumns(10);
		add(lon);

		description = new JTextArea();
		description.setToolTipText("The location of the device.");
		description.setBackground(Color.LIGHT_GRAY);
		description.setBounds(296, 154, 261, 85);
		add(description);

		JLabel lblSiteName = new JLabel("Site Name");
		lblSiteName.setBounds(211, 64, 64, 14);
		add(lblSiteName);

		JLabel lblLat = new JLabel("Latitude");
		lblLat.setBounds(211, 95, 46, 14);
		add(lblLat);

		JLabel lblLon = new JLabel("Longitude");
		lblLon.setBounds(211, 126, 69, 14);
		add(lblLon);

		JLabel lblDesrcpiton = new JLabel("Description");
		lblDesrcpiton.setBounds(211, 159, 75, 14);
		add(lblDesrcpiton);

		JLabel lblDevice = new JLabel("Device");
		lblDevice.setBounds(211, 253, 46, 14);
		add(lblDevice);

		devices = new JComboBox<DeviceHandler>();
		for (DeviceHandler d : IButtonApp.getApaters()) {
			devices.addItem(d);
		}
		devices.setBounds(296, 250, 125, 20);
		add(devices);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBackground(Color.LIGHT_GRAY);
		btnUpdate.addActionListener(this);
		btnUpdate.setBounds(656, 412, 89, 23);
		add(btnUpdate);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		btnBack.setBounds(755, 412, 89, 23);
		add(btnBack);
		
		JLabel lblWaringChangingA = new JLabel("Warning: Changing a site's device will reset its temperature readings!");
		lblWaringChangingA.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWaringChangingA.setBounds(211, 330, 467, 14);
		add(lblWaringChangingA);
	}

	public void setSite(Site site) {
		this.site = site;
		siteName.setText(site.siteName);
		lat.setText(Double.toString(site.siteLatitude));
		lon.setText(Double.toString(site.siteLongitude));
		description.setText(site.siteDescription);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}
		
		if (action.getActionCommand() == "Update") {

			try {
				Site.editSite(site.id, siteName.getText(), lat.getText(), lon.getText(), description.getText());
				DeviceHandler deviceOld = site.device;
				DeviceHandler deviceNew = (DeviceHandler) devices.getSelectedItem();

				site.device = deviceNew;

				if (deviceNew != null) {
					MissionContainer ms = (MissionContainer) deviceNew.device;
					SiteData.updateSite(site.id, deviceNew.getAddress());
					if (deviceOld == null || deviceNew.getAddress() != deviceOld.getAddress()) {
						try {
							MissionHandler.startMission(deviceNew.adapter, ms);
						} catch (OneWireException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					SiteData.updateSite(site.id, "null");
				}
				
				IButtonApp.loadSites(IButtonApp.user, IButtonApp.pass);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
			IButtonApp.showCard("Dashboard");

		}
	}
}
