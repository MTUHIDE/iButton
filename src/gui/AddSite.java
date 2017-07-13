package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import app.IButtonApp;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import ibutton.MissionHandler;
import network.DeviceController;
import network.NetworkController;
import network.SiteController;
import okhttp3.Response;
import output.Logger;
import user.Device;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;

/**
 * The JPanel for the adding a site GUI. This JPanel displays all of the
 * required fields for adding a site. (name, latitude, longitude, and
 * description) As well a field to assign a iButton iButton to a site.
 * 
 * @author Justin Havely
 *
 */
public class AddSite extends GUI {
	private static final long serialVersionUID = 1L;

	private JTextField siteName, lat, lon;
	private JTextArea description;
	private JComboBox<Device> devices;

	/**
	 * Create and adds all the components to the JPanel.
	 */
	public AddSite() {
	    super("Add Site");
		setBackground(Color.WHITE);
		setLayout(null);

		// name field
		siteName = new JTextField();
		siteName.setToolTipText("E.g: My House");
		siteName.setBounds(296, 61, 261, 20);
		siteName.setColumns(10);
		add(siteName);

		// latitude field
		lat = new JTextField();
		lat.setToolTipText("E.g: 11.22321");
		lat.setBounds(296, 92, 86, 20);
		lat.setColumns(10);
		add(lat);

		// longitude field
		lon = new JTextField();
		lon.setToolTipText("E.g: 11.22321");
		lon.setBounds(471, 92, 86, 20);
		lon.setColumns(10);
		add(lon);

		// description field
		description = new JTextArea();
		description.setToolTipText("I.e: The location of the iButton.");
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		description.setBounds(296, 123, 261, 85);
		add(description);

		// labels for the fields
		JLabel lblSiteName = new JLabel("Site Name");
		lblSiteName.setBounds(211, 64, 64, 14);
		add(lblSiteName);

		JLabel lblLat = new JLabel("Latitude");
		lblLat.setBounds(211, 95, 46, 14);
		add(lblLat);

		JLabel lblLon = new JLabel("Longitude");
		lblLon.setBounds(405, 95, 69, 14);
		add(lblLon);

		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(211, 128, 75, 14);
		add(lblDescription);

		JLabel lblDevice = new JLabel("Device");
		lblDevice.setBounds(211, 222, 46, 14);
		add(lblDevice);

		// Drop down box of plugged in iButton devices
		devices = new JComboBox<Device>();
        add(devices);

		// Add site button
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.LIGHT_GRAY);
		btnAdd.addActionListener(this);
		btnAdd.setBounds(656, 412, 89, 23);
		add(btnAdd);

		// Back button
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.addActionListener(this);
		btnBack.setBounds(755, 412, 89, 23);
		add(btnBack);
	}


	public void update(){
	    devices.removeAllItems();
        // Add iButton devices to drop down box
        for (Device d : NetworkController.currentLoggedInUser.getDevices()) {
            devices.addItem(d);
        }
        devices.setBounds(296, 219, 125, 20);
    }

	/**
	 * Listens for mouse clicks and contains the logic for adding a new site.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		// Shows the last JPanel
		if (action.getActionCommand() == "Back") {
			IButtonApp.showPreviousCard();
		}
		if (action.getActionCommand() == "Add") {
			// Creates a new site (server side) and gets the site's ID.
			Response response = SiteController.newSite(siteName.getText(), lat.getText(), lon.getText(), description.getText());
			String siteID;
			// Checks if HTTP request was valid
			if (response.isSuccessful()) {
				//Creates a local
			    siteID = response.request().url().queryParameterValue(0);

				response.close();
			} else {
				JOptionPane.showMessageDialog(this, "Could not create site. Please check your entries.");
				response.close();
				return;
			}

			// Gets the selected iButton iButton from the drop down box.
			Device device = (Device) devices.getSelectedItem();

		/*	if (device != null) {
				// Loads the iButton iButton's missionContanier
				MissionContainer ms = (MissionContainer) device.iButton;

				// Checks if another site has this iButton iButton assigned to it
				String[] siteCheck = SiteData.findSiteByDevice(device.getAddress());

				if (siteCheck != null) {
					// Set old site's iButton to null
					SiteData.updateSite(siteCheck[0], "null");
				}

				SiteData.addSite(siteID, device.getAddress());

				try {
					// Starts a new mission.
					MissionHandler.startMission(device.adapter, ms);
				} catch (OneWireException e) {
					Logger.writeErrorToLog(e);
				}
			} else {
				SiteData.addSite(siteID, "null");
			}

*/
			IButtonApp.showCard(GUI.dashboard);
		}
	}

}
