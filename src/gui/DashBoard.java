package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import handlers.DeviceHandler;
import handlers.MissionHandler;
import handlers.MissionSamples;
import network.Site;
import network.Upload;
import output.TempData;
import output.Logger;
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
 * The GUI for showing all of the users sites and functions.
 * 
 * @author Justin Havely
 *
 */
public class DashBoard extends JPanel implements ListSelectionListener, ActionListener {
	private static final long serialVersionUID = 1L;

	private JList<Site> list;
	private JTextArea info;
	private JButton btnAddSite, btnUpload, btnSettings, btnLogOut, btnEditSite;

	/**
	 * Adds all the components to the JPanel.
	 */
	public DashBoard() {
		setBackground(Color.WHITE);
		setLayout(null);

		list = new JList<Site>();
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.WHITE);
		list.setBounds(10, 28, 177, 400);
		list.addListSelectionListener(this);
		add(list);

		btnAddSite = new JButton("Add Site");
		btnAddSite.addActionListener(this);
		btnAddSite.setBackground(Color.LIGHT_GRAY);
		btnAddSite.setBounds(755, 28, 89, 23);
		add(btnAddSite);

		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(this);
		btnSettings.setBackground(Color.LIGHT_GRAY);
		btnSettings.setBounds(755, 372, 89, 23);
		add(btnSettings);

		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(this);
		btnLogOut.setBackground(Color.LIGHT_GRAY);
		btnLogOut.setBounds(755, 406, 89, 23);
		add(btnLogOut);

		// The subJPanel that contains info, edit, and upload components. Mainly
		// for looks.
		JPanel panel = new JPanel();
		panel.setBounds(197, 28, 548, 400);
		add(panel);
		panel.setLayout(null);

		info = new JTextArea();
		info.setBounds(10, 11, 409, 378);
		info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		info.setEditable(false);
		info.setBackground(Color.WHITE);
		panel.add(info);

		btnUpload = new JButton("Upload");
		btnUpload.setBounds(429, 12, 109, 23);
		btnUpload.setEnabled(false);
		btnUpload.addActionListener(this);
		btnUpload.setBackground(Color.LIGHT_GRAY);
		panel.add(btnUpload);

		btnEditSite = new JButton("Edit Site");
		btnEditSite.addActionListener(this);
		btnEditSite.setEnabled(false);
		btnEditSite.setBackground(Color.LIGHT_GRAY);
		btnEditSite.setBounds(429, 46, 109, 23);
		panel.add(btnEditSite);
	}

	/**
	 * Updates the sites list in the dashboard. This should be call when ever a
	 * site is added or edited.
	 * 
	 * @param sites
	 *            The array of sites to show on the dashboard.
	 */
	public void updateSiteList(Site[] sites) {
		DefaultListModel<Site> model = new DefaultListModel<Site>();

		for (Site d : sites) {
			model.addElement(d);
		}

		list.setModel(model);
		info.setText("");
	}

	/**
	 * Listens for a change on the selected site.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (list.getSelectedValue() == null) {
			btnEditSite.setEnabled(false);
			btnUpload.setEnabled(false);
		} else {
			info.setText(list.getSelectedValue().getInfo());
			btnEditSite.setEnabled(true);

			if (list.getSelectedValue().device != null) {
				btnUpload.setEnabled(true);
			} else {
				btnUpload.setEnabled(false);
			}

		}
	}

	/**
	 * Listen for button clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand() == "Add Site") {
			IButtonApp.showCard("Add Site");
		}

		if (action.getActionCommand() == "Upload") {
			// Gets the site, device, and the device mission container.
			Site site = list.getSelectedValue();
			DeviceHandler device = site.device;
			MissionContainer ms = (MissionContainer) device.device;

			try {
				try {
					// Temperature readings
					MissionSamples samples = MissionHandler.getMissonTemperatureData(device.adapter, ms);

					if (samples.getLength() < 1) {
						// No readings to upload.
						JOptionPane.showMessageDialog(this, "No temperatures taken. Please wait an hour.");
						return;
					}

					// Creates local csv file.
					TempData tempFile = new TempData(device.getAddress(), samples);
					tempFile.writeDataFile();

					// Uploads file to server.
					Upload.uploadFile(site, new File(tempFile.location));
					JOptionPane.showMessageDialog(this, "Upload Successful");

				} catch (IOException e) {
					Logger.writeErrorToLog(e);
				}
				Logger.writeToLog("Wrote data");

			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}

		}

		if (action.getActionCommand() == "Settings") {
			IButtonApp.showCard("Settings");		
		}
		if (action.getActionCommand() == "Log Out") {
			IButtonApp.showCard("Login");
		}
		if (action.getActionCommand() == "Edit Site") {
			IButtonApp.editSite.setSite(list.getSelectedValue());
			IButtonApp.showCard("Edit Site");
		}
	}
}
