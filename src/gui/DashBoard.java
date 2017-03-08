package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import network.Authentication;
import network.Site;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;

public class DashBoard extends JPanel implements ListSelectionListener {
	private static final long serialVersionUID = 1L;
	private JList<Site> list;
	private JTextArea info;

	public DashBoard(Site[] sites) {
		DefaultListModel<Site> model = new DefaultListModel<Site>();
		setBackground(Color.WHITE);
		setLayout(null);
		for (Site d : sites) {
			model.addElement(d);
		}

		list = new JList<Site>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(Color.LIGHT_GRAY);
		list.setBounds(10, 28, 177, 250);
		list.addListSelectionListener(this);

		info = new JTextArea();
		info.setEditable(false);
		info.setBackground(Color.LIGHT_GRAY);
		info.setSize(427, 250);
		info.setLocation(197, 28);

		add(list);
		add(info);
		
		JButton btnAddSite = new JButton("Add Site");
		btnAddSite.setBounds(634, 25, 89, 23);
		add(btnAddSite);
		
		JButton btnStartMission = new JButton("Start Mission");
		btnStartMission.setBounds(634, 59, 89, 23);
		add(btnStartMission);
		
		JButton btnStopMission = new JButton("Stop Mission");
		btnStopMission.setBounds(634, 93, 89, 23);
		add(btnStopMission);
		
		JButton btnUpload = new JButton("Upload");
		btnUpload.setBounds(634, 127, 89, 23);
		add(btnUpload);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setBounds(634, 161, 89, 23);
		add(btnSettings);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setBounds(634, 195, 89, 23);
		add(btnLogOut);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<Site> lsm = (JList<Site>) e.getSource();
		info.setText(lsm.getSelectedValue().getInfo());
	}
}
