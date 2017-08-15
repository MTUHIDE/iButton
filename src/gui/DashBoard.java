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
import network.DeviceController;
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
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The JPanel for showing the users sites and upload, edit site, and add site
 * functions. This GUI is the main hub for the user.
 * 
 * @author Justin Havely
 *
 */
public class DashBoard extends GUI implements ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private JList<Device> list; // All of the user's sites
	private JTextArea info; // Displays information about the selected site
	private JButton btnUpload, btnAbout, btnLogOut, btnEditSite;

	/**
	 * Creates and adds all of the components to the JPanel.
	 */
	public DashBoard() {
		super("Dashboard");
		setBackground(Color.WHITE);
		setLayout(null);

		// The list of the user's sites
		list = new JList<Device>();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.WHITE);
		list.setBounds(10, 28, 177, 400);
		list.addListSelectionListener(this);
		add(list);

		// Setting Button
		btnAbout = new JButton("About");
		btnAbout.addActionListener(this);
		btnAbout.setBackground(Color.LIGHT_GRAY);
		btnAbout.setBounds(755, 372, 89, 23);
		add(btnAbout);

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
		DefaultListModel<Device> model = new DefaultListModel<Device>();
		List<Device> devices = NetworkController.currentLoggedInUser.getDevices();

		for (Device d : devices) {
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
        info.setText("");
		if (list.getSelectedValue() == null) {
			btnEditSite.setEnabled(false);
			btnUpload.setEnabled(false);

		} else {
            btnEditSite.setEnabled(true);

            Site site = NetworkController.currentLoggedInUser.getSite(list.getSelectedValue().siteID);

			if(site != null){
                info.setText(site.getInfo());

                // Enables the upload button if device has an site
                if (list.getSelectedValue().iButton != null) {
                    btnUpload.setEnabled(true);
                } else {
                    btnUpload.setEnabled(false);
                }
            }
		}
	}

	/**
	 * Listen for mouse clicks and contains the logic for uploading data from an
	 * iButton iButton.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Upload")) {
			// Gets the siteController, iButton, and the iButton mission container.
			Site site = NetworkController.currentLoggedInUser.getSite(list.getSelectedValue().siteID);
			Device device = list.getSelectedValue();
			MissionContainer ms = (MissionContainer) device.iButton;

			try {
				try {
					// Gets temperature readings
					MissionSamples samples = MissionHandler.getMissionTemperatureData(device.iButton.getAdapter(), ms);

					if (samples.getLength() < 1) {
						// No readings to upload.
						JOptionPane.showMessageDialog(this, "No temperatures taken. Please wait an hour.");
						return;
					}

					// Creates local CSV file with temperature data.
					TemperatureData tempFile = new TemperatureData(device.manufacture_num, samples);
					tempFile.writeDataFile();

					// Uploads file to server and logs the action.
					if (UploadController.uploadFile(site, new File(tempFile.location))) {
						JOptionPane.showMessageDialog(this, "Upload Successful");
						Logger.writeToLog("Wrote data to:" + site.getInfo() + "from: " + device.manufacture_num);
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
		if (action.getActionCommand().equals("About")) {
			IButtonApp.showCard(GUI.about);
		}
		// Shows login JPanel
		if (action.getActionCommand().equals("Logout")) {
			LoginController.logout();
			IButtonApp.showCard(GUI.login);
		}
		// Shows edit site JPanel
		if (action.getActionCommand().equals("Edit Site")) {

            Device device = list.getSelectedValue();
            MissionContainer ms = (MissionContainer) device.iButton;

            Site site = (Site) JOptionPane.showInputDialog(this, "Pick a site",
                    "", JOptionPane.QUESTION_MESSAGE, null,
                    NetworkController.currentLoggedInUser.getSites().toArray(),
                    NetworkController.currentLoggedInUser.getSites().get(0));

            if(site == null){
                return;
            }

            if(device.id != null) {
                try {
                    MissionHandler.startMission(device.iButton.getAdapter(), ms);
                    DeviceController.editDevice(device.id, site.id, device.type, device.manufacture_num);
                } catch (OneWireException e) {
                    Logger.writeErrorToLog(e);
                }
            } else {
                try {
                    MissionHandler.startMission(device.iButton.getAdapter(), ms);
                    DeviceController.addDevice(site.id, device.type, device.manufacture_num);
                } catch (OneWireException e) {
                    Logger.writeErrorToLog(e);
                }
            }
            update();
		}
	}
}
