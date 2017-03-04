package gui;

import javax.swing.JPanel;

import com.dalsemi.onewire.OneWireException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import handlers.DeviceHandler;
import network.Authentication;

import java.awt.Color;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public class DashBoard extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public DashBoard() {
		setBackground(Color.WHITE);
		setLayout(null);

		try {
			List<DeviceHandler> devices = DeviceHandler.getDevices(DeviceHandler.deviceDefaultName, DeviceHandler.adapterDefaultName);

			DefaultListModel<DeviceHandler> model = new DefaultListModel<DeviceHandler>();

			for (DeviceHandler d : devices) {
				model.addElement(d);
			}
			
			JList<DeviceHandler> list = new JList<DeviceHandler>(model);

			list.setBackground(Color.LIGHT_GRAY);
			list.setBounds(28, 28, 177, 423);
			add(list);

		} catch (OneWireException e) {
			//e.printStackTrace();
		}
		
	}

}
