package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import handlers.FileHandler;
import network.Site;
import network.Upload;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DashBoard extends JPanel implements ListSelectionListener, ActionListener {
	public DashBoard() {
	}

	private static final long serialVersionUID = 1L;
	private JList<Site> list;
	private JTextArea info;
	private Site[] sites;
	private JButton btnAddSite, btnStartMission, btnStopMission, btnUpload, btnSettings, btnLogOut;

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

		btnStartMission = new JButton("Start Mission");
		btnStartMission.addActionListener(this);
		btnStartMission.setBackground(Color.LIGHT_GRAY);
		btnStartMission.setBounds(634, 66, 89, 23);
		add(btnStartMission);

		btnStopMission = new JButton("Stop Mission");
		btnStopMission.addActionListener(this);
		btnStopMission.setBackground(Color.LIGHT_GRAY);
		btnStopMission.setBounds(634, 100, 89, 23);
		add(btnStopMission);

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
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<Site> lsm = (JList<Site>) e.getSource();
		info.setText(lsm.getSelectedValue().getInfo());
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		System.out.println(action.getActionCommand());
		if (action.getSource() == btnAddSite) {
			
		}
		if (action.getSource() == btnStartMission) {

		}
		if (action.getSource() == btnStopMission) {

		}
		if (action.getSource() == btnUpload) {
			Upload.uploadFile(sites[0],
					new File(FileHandler.DATA_FOLDER + "/" + "C600000033B2B141_1489122841000" + "_dat.csv"));
		}
		if (action.getSource() == btnSettings) {

		}
		if (action.getActionCommand() == "Log Out") {
			IButtonApp.logout();
		}
	}
}
