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
import javax.swing.border.EtchedBorder;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import handlers.DeviceHandler;
import handlers.MissionHandler;
import network.Site;
import output.Logger;
import output.SiteData;
import java.awt.Font;
import java.awt.SystemColor;

/**
 * The GUI for editing a site. Call set site before showing this JPanel.
 * 
 * @author Justin Havely
 *
 */
public class EditSite extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextField siteName, lat, lon;
	private JTextArea description;
	private JComboBox<DeviceHandler> devices;
	private Site site;

	/**
	 * Adds all the components to the JPanel.
	 */
	public EditSite() {
		setBackground(Color.WHITE);
		setLayout(null);

		siteName = new JTextField();
		siteName.setToolTipText("E.g: My House");
		siteName.setBounds(296, 61, 261, 20);
		siteName.setColumns(10);
		add(siteName);

		lat = new JTextField();
		lat.setToolTipText("E.g: 11.22321");
		lat.setBounds(296, 92, 261, 20);
		lat.setColumns(10);
		add(lat);

		lon = new JTextField();
		lon.setToolTipText("E.g: 11.22321");
		lon.setBounds(296, 123, 261, 20);
		lon.setColumns(10);
		add(lon);

		description = new JTextArea();
		description.setToolTipText("I.e: The location of the device.");
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setBounds(296, 154, 261, 85);
		description.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
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
		for (DeviceHandler d : IButtonApp.getDevices()) {
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

		JLabel lblWaringChangingA = new JLabel(
				"Warning: Changing a site's device will reset its temperature readings!");
		lblWaringChangingA.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWaringChangingA.setBounds(211, 330, 467, 14);
		add(lblWaringChangingA);
	}

	/**
	 * Set the fields to the site current values
	 * 
	 * @param site
	 *            The site to update
	 */
	public void setSite(Site site) {
		this.site = site;
		siteName.setText(site.siteName);
		lat.setText(Double.toString(site.siteLatitude));
		lon.setText(Double.toString(site.siteLongitude));
		description.setText(site.siteDescription);
	}

	/**
	 * Listens for button clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}

		if (action.getActionCommand() == "Update") {
			try {
				// Updates server side.
				Site.editSite(site.id, siteName.getText(), lat.getText(), lon.getText(), description.getText());

				// Saves old and new devices and updates the site's device.
				DeviceHandler deviceOld = site.device;
				DeviceHandler deviceNew = (DeviceHandler) devices.getSelectedItem();
				site.device = deviceNew;

				if (deviceNew != null) {
					// Load the device missionContanier and updates the local
					// siteData file.
					MissionContainer ms = (MissionContainer) deviceNew.device;
					SiteData.updateSite(site.id, deviceNew.getAddress());

					if (deviceOld == null || deviceNew.getAddress().equals(deviceOld.getAddress())) {
						// Starts a new mission.
						try {
							MissionHandler.startMission(deviceNew.adapter, ms);
						} catch (OneWireException e) {
							Logger.writeErrorToLog(e);
						}
					}

				} else {
					SiteData.updateSite(site.id, "null");
				}

				// Reloads the sites to update the dashboard.
				IButtonApp.loadSites();

			} catch (IOException e) {
				Logger.writeErrorToLog(e);
			}

			IButtonApp.showCard("Dashboard");
		}
	}
}
