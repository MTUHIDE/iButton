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
import network.Site;
import network.Upload;
import output.DataFile;
import output.Logger;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
	private Site[] sites;
	private JButton btnAddSite, btnUpload, btnSettings, btnLogOut;
	private JComboBox<DeviceHandler> comboBox;

	public void setSites(Site[] sites) {
		this.sites = sites;
	}

	public void createAndShowGUI() {
		DefaultListModel<Site> model = new DefaultListModel<Site>();
		setBackground(Color.WHITE);
		setLayout(null);
		for (Site d : sites) {
			model.addElement(d);
		}

		list = new JList<Site>(model);
		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.WHITE);
		list.setBounds(10, 28, 177, 400);
		list.addListSelectionListener(this);

		info = new JTextArea();
		info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
		info.setEditable(false);
		info.setBackground(Color.WHITE);
		info.setSize(427, 400);
		info.setLocation(197, 28);

		add(list);
		add(info);

		btnAddSite = new JButton("Add Site");
		btnAddSite.addActionListener(this);
		btnAddSite.setBackground(Color.LIGHT_GRAY);
		btnAddSite.setBounds(634, 32, 89, 23);
		add(btnAddSite);

		btnUpload = new JButton("Upload");
		btnUpload.addActionListener(this);
		btnUpload.setBackground(Color.LIGHT_GRAY);
		btnUpload.setBounds(634, 134, 89, 23);
		add(btnUpload);

		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(this);
		btnSettings.setBackground(Color.LIGHT_GRAY);
		btnSettings.setBounds(634, 168, 89, 23);
		add(btnSettings);

		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(this);
		btnLogOut.setBackground(Color.LIGHT_GRAY);
		btnLogOut.setBounds(634, 202, 89, 23);
		add(btnLogOut);

		comboBox = new JComboBox<DeviceHandler>();
		comboBox.setBounds(696, 239, 86, 20);
		comboBox.setEnabled(false);
		add(comboBox);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<Site> lsm = (JList<Site>) e.getSource();


		if (lsm.getSelectedValue().device == null) {
			comboBox.removeAllItems();
			comboBox.setEnabled(true);
			for (DeviceHandler d : IButtonApp.getApaters()) {
				comboBox.addItem(d);
			}

		}
		
		info.setText(lsm.getSelectedValue().getInfo());

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
					DataFile df = new DataFile(device.getAddress(),
							MissionHandler.getMissonTemperatureData(device.adapter, ms));
					df.writeDataFile();
					Upload.uploadFile(site, new File(df.location));
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
	}
}
