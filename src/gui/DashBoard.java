package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import network.Site;

import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JList;

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
		info.setSize(427, 250);
		info.setLocation(259, 28);
		info.setEditable(false);
		
		
		add(list);
		add(info);

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		@SuppressWarnings("unchecked")
		JList<Site> lsm = (JList<Site>) e.getSource();
		info.setText(lsm.getSelectedValue().getInfo());

	}
}
