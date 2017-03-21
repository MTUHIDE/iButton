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

public class DashBoard extends JPanel implements ListSelectionListener, ActionListener {

	private static final long serialVersionUID = 1L;

	private JList<Site> list;
	private JTextArea info;
	// private Site[] sites;
	private JButton btnAddSite, btnUpload, btnSettings, btnLogOut, btnEditSite;

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
		// Panel
		JPanel panel = new JPanel();
		panel.setBounds(197, 28, 548, 400);
		add(panel);
		panel.setLayout(null);

		info = new JTextArea();
		info.setBounds(10, 11, 409, 378);
		panel.add(info);
		info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		info.setEditable(false);
		info.setBackground(Color.WHITE);

		btnUpload = new JButton("Upload");
		btnUpload.setBounds(429, 12, 109, 23);
		btnUpload.setEnabled(false);
		panel.add(btnUpload);
		btnUpload.addActionListener(this);
		btnUpload.setBackground(Color.LIGHT_GRAY);

		btnEditSite = new JButton("Edit Site");
		btnEditSite.addActionListener(this);
		btnEditSite.setEnabled(false);
		btnEditSite.setBackground(Color.LIGHT_GRAY);
		btnEditSite.setBounds(429, 46, 109, 23);
		panel.add(btnEditSite);
	}

	public void updateSiteList(Site[] sites) {
		DefaultListModel<Site> model = new DefaultListModel<Site>();

		for (Site d : sites) {
			model.addElement(d);
		}

		list.setModel(model);
		info.setText("");

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		@SuppressWarnings("unchecked")
		JList<Site> lsm = (JList<Site>) e.getSource();
		if (lsm.getSelectedValue() == null) {
			return;
		}
		info.setText(lsm.getSelectedValue().getInfo());

		if (list.getSelectedValue() != null) {
			btnEditSite.setEnabled(true);
			if (list.getSelectedValue().device != null) {
				btnUpload.setEnabled(true);
			} else {
				btnUpload.setEnabled(false);
			}
		} else {
			btnEditSite.setEnabled(false);
			btnUpload.setEnabled(false);
		}

	}

	@Override
	public void actionPerformed(ActionEvent action) {

		if (action.getActionCommand() == "Add Site") {
			IButtonApp.showCard("Add Site");
		}
		if (action.getActionCommand() == "Upload") {
			Site site = list.getSelectedValue();
			DeviceHandler device = site.device;
			MissionContainer ms = (MissionContainer) device.device;
			try {
				try {
					MissionSamples samples = MissionHandler.getMissonTemperatureData(device.adapter, ms);
					if (samples.getLength() < 1) {
						JOptionPane.showMessageDialog(this, "No temperatures taken. Please wait an hour.");
						return;
					}
					TempData df = new TempData(device.getAddress(), samples);
					df.writeDataFile();
					Upload.uploadFile(site, new File(df.location));
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
