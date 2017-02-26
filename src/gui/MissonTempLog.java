package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import handlers.DeviceHandler;
import handlers.MissionHandler;
import output.DataFile;
import output.Logger;

/**
 * Demos the current features so far program in a GUI.
 * 
 * @author Justin Havely
 *
 */
public class MissonTempLog extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextArea log = new JTextArea(10, 40);
	private JButton startMission = new JButton("Start Mission");
	private JButton stopMission = new JButton("Stop Mission");
	private JButton uploadData = new JButton("Upload Data");
	
	private List<DeviceHandler> devices;
	private DeviceHandler activeDevice;

	/**
	 * Sets up the JFrame
	 */
	public MissonTempLog() {
		log.setEditable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		startMission.addActionListener(this);
		stopMission.addActionListener(this);
		uploadData.addActionListener(this);
		add(log);
		add(startMission);
		add(stopMission);
		add(uploadData);
		pack();
		setVisible(true);
	}

	/**
	 * 
	 * Creates GUI and checks for an iButton adapter.
	 * 
	 * @param args
	 *            Does nothing.
	 */
	public static void main(String[] args) {
		MissonTempLog misson = new MissonTempLog();
		misson.load();
	}

	public void load(){
		try {
			devices = DeviceHandler.getDevices(DeviceHandler.deviceDefaultName, DeviceHandler.adapterDefaultName);
			activeDevice = devices.get(0);
			Logger.writeToLog("Found adapters");
			log.append("Found Adapters!\n");
		} catch (OneWireException e) {
			Logger.writeErrorToLog(e);
			log.append("Could not Find Adapters!\n");
		}
	}
	
	/**
	 * Listens for button clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		MissionContainer ms = (MissionContainer) activeDevice.device;

		if (a.getSource() == startMission) {
			log.append("Starting misson!\n");
			try {
				MissionHandler.startMission(activeDevice.adapter, ms);
				Logger.writeToLog("Started new mission");
			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}
		}
		if (a.getSource() == stopMission) {
			log.append("Stoping misson!\n");
			try {
				MissionHandler.stopMission(activeDevice.adapter, ms);
				Logger.writeToLog("Stop mission");
			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}
		}
		if (a.getSource() == uploadData) {
			log.append("Loading Mission...\n");
			try {
				log.append("Writting to file...\n");
				try { // TODO Should be ran on a new thread so the GUI does not
						// freeze.
					new DataFile(activeDevice.getAddress(), MissionHandler.getMissonTemperatureData(activeDevice.adapter, ms))
							.writeDataFile();
				} catch (IOException e) {
					Logger.writeErrorToLog(e);
				}
				Logger.writeToLog("Wrote data");
				log.append("Done!\n");
			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}
		}

	}

}
