package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.IButtonApp;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import ibutton.MissionHandler;
import ibutton.MissionSamples;
import network.LoginController;
import network.NetworkController;
import network.UploadController;
import output.TemperatureData;
import output.Logger;
import user.Device;
import user.Site;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The JPanel for showing the users sites and upload, edit site, and add site
 * functions. This GUI is the main hub for the user.
 * 
 * @author Justin Havely
 *
 */
public class DashBoard extends GUI implements ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private JList<Site> list; // All of the user's sites
	private JTextArea info; // Displays information about the selected site
	private JButton btnAddSite, btnUpload, btnSettings, btnLogOut, btnEditSite;

	/**
	 * Creates and adds all of the components to the JPanel.
	 */
	public DashBoard() {
		super("Dashboard");
		setBackground(Color.WHITE);
		setLayout(null);

		// The list of the user's sites
		list = new JList<Site>();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.WHITE);
		list.setBounds(10, 28, 177, 400);
		list.addListSelectionListener(this);
		add(list);

		// Add SiteController Button
		btnAddSite = new JButton("Add Site");
		btnAddSite.addActionListener(this);
		btnAddSite.setBackground(Color.LIGHT_GRAY);
		btnAddSite.setBounds(755, 28, 89, 23);
		add(btnAddSite);

		// Setting Button
		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(this);
		btnSettings.setBackground(Color.LIGHT_GRAY);
		btnSettings.setBounds(755, 372, 89, 23);
		add(btnSettings);

		// Logout Button
		btnLogOut = new JButton("Logout");
		btnLogOut.addActionListener(this);
		btnLogOut.setBackground(Color.LIGHT_GRAY);
		btnLogOut.setBounds(755, 406, 89, 23);
		add(btnLogOut);

		// The subJPanel that contains site information, edit site, and upload
		// components. Mainly for looks.
		JPanel panel = new JPanel();
		panel.setBounds(197, 28, 548, 400);
		add(panel);
		panel.setLayout(null);

		// Text box that displays site information
		info = new JTextArea();
		info.setBounds(10, 11, 409, 378);
		info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		info.setEditable(false);
		info.setBackground(Color.WHITE);
		panel.add(info);

		// UploadController Button
		btnUpload = new JButton("Upload");
		btnUpload.setBounds(429, 12, 109, 23);
		btnUpload.setEnabled(false);
		btnUpload.addActionListener(this);
		btnUpload.setBackground(Color.LIGHT_GRAY);
		panel.add(btnUpload);

		// Edit SiteController Button
		btnEditSite = new JButton("Edit Site");
		btnEditSite.addActionListener(this);
		btnEditSite.setEnabled(false);
		btnEditSite.setBackground(Color.LIGHT_GRAY);
		btnEditSite.setBounds(429, 46, 109, 23);
		panel.add(btnEditSite);
	}

	/**
	 * Updates the siteControllers list in the dashboard. This should be call when ever a
	 * site is added or edited.
	 *
	 */
	public void update() {
		DefaultListModel<Site> model = new DefaultListModel<Site>();
		Site[] sites = NetworkController.currentLoggedInUser.getSites();

		for (Site d : sites) {
			model.addElement(d);
		}

		list.setModel(model);
	}

	/**
	 * Listens for when the selected site is changed. Sets the edit and upload
	 * buttons to be active or not.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedValue() == null) {
			btnEditSite.setEnabled(false);
			btnUpload.setEnabled(false);
			info.setText("");
		} else {
			info.setText(list.getSelectedValue().getInfo());
			btnEditSite.setEnabled(true);

			// Enables the upload button if site has a iButton iButton
			if (list.getSelectedValue().devices != null) {
				btnUpload.setEnabled(true);
			} else {
				btnUpload.setEnabled(false);
			}
		}
	}

	/**
	 * Listen for mouse clicks and contains the logic for uploading data from an
	 * iButton iButton.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		// Shows Add SiteController JPanel
		if (action.getActionCommand() == "Add Site") {
			IButtonApp.showCard(GUI.Addsite);
		}

		if (action.getActionCommand() == "Upload") {
			// Gets the siteController, iButton, and the iButton mission container.
			Site site = list.getSelectedValue();
			Device device = site.devices[0];
			MissionContainer ms = (MissionContainer) device.iButton;

			try {
				try {
					// Gets temperature readings
					MissionSamples samples = MissionHandler.getMissonTemperatureData(device.adapter, ms);

					if (samples.getLength() < 1) {
						// No readings to upload.
						JOptionPane.showMessageDialog(this, "No temperatures taken. Please wait an hour.");
						return;
					}

					// Creates local CSV file with temperature data.
					TemperatureData tempFile = new TemperatureData(device.getAddress(), samples);
					tempFile.writeDataFile();

					// Uploads file to server and logs the action.
					if (UploadController.uploadFile(site, new File(tempFile.location))) {
						JOptionPane.showMessageDialog(this, "Upload Successful");
						Logger.writeToLog("Wrote data to:" + site.getInfo() + "from: " + device.getAddress());
					} else {
						JOptionPane.showMessageDialog(this, "Upload Failed");
					}

				} catch (IOException e) {
					Logger.writeErrorToLog(e);
				}

			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}

		}
		// Shows settings JPanel
		if (action.getActionCommand() == "Settings") {
			IButtonApp.showCard(GUI.settings);
		}
		// Shows login JPanel
		if (action.getActionCommand() == "Logout") {
			LoginController.logout();
			IButtonApp.showCard(GUI.login);
		}
		// Shows edit site JPanel
		if (action.getActionCommand() == "Edit Site") {
			GUI.editSite.setSite(list.getSelectedValue());
			IButtonApp.showCard(GUI.editSite);
		}
	}
}
