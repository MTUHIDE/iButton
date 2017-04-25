package examples;

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
import handlers.MissionSamples;
import output.TempData;

/**
 * Demos how to use handlers to communicate with a iButton device.
 * 
 * @author Justin Havely
 *
 */
public class DeviceController extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextArea log = new JTextArea(10, 40); // Displays helpful
													// information
	private JButton startMission = new JButton("Start Mission");
	private JButton stopMission = new JButton("Stop Mission");
	private JButton readData = new JButton("Read Data");

	private List<DeviceHandler> devices; // All connected iButton devices
	private DeviceHandler activeDevice; // The current iButton device being read

	/**
	 * Sets up the JFrame and components
	 */
	public DeviceController() {
		setLayout(new FlowLayout());

		log.setEditable(false);
		startMission.addActionListener(this);
		stopMission.addActionListener(this);
		readData.addActionListener(this);

		add(log);
		add(startMission);
		add(stopMission);
		add(readData);

		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * 
	 * Creates GUI and checks for an iButton devices.
	 * 
	 * @param args
	 *            Does nothing.
	 */
	public static void main(String[] args) {
		DeviceController dc = new DeviceController();
		dc.load();
	}

	/**
	 * Searches all ports for the default adapters and devices then sets the
	 * first one found as the active device.
	 */
	public void load() {
		try {
			devices = DeviceHandler.getDevices(DeviceHandler.deviceDefaultName, DeviceHandler.adapterDefaultName);
			activeDevice = devices.get(0); // Change the value '0' to set a
											// different iButton, if multiple
											// iButtons are plugged in
			log.append("Found Adapters!\n");
		} catch (OneWireException e) {
			log.append("Could not Find Adapters!\n");
		}
	}

	/**
	 * Listens for mouse clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		MissionContainer ms = (MissionContainer) activeDevice.device;
		// Starts a new mission with default settings
		if (a.getSource() == startMission) {
			log.append("Starting misson\n");
			try {
				MissionHandler.startMission(activeDevice.adapter, ms);
			} catch (OneWireException e) {
				log.append("Failed to start misson!\n");
			}
		}
		// Stops the current mission
		if (a.getSource() == stopMission) {
			log.append("Stoping misson\n");
			try {
				MissionHandler.stopMission(activeDevice.adapter, ms);
			} catch (OneWireException e) {
				log.append("Failed to stop misson!\n");
			}
		}
		// Writes mission samples into the data folder
		// (C://user/AppData/Roaming/iButtonData)
		if (a.getSource() == readData) {
			try {
				log.append("Loading Mission...\n");
				MissionSamples samples = MissionHandler.getMissonTemperatureData(activeDevice.adapter, ms);
				log.append("Writting to file...\n");
				new TempData(activeDevice.getAddress(), samples).writeDataFile();
				log.append("Done!\n");
			} catch (IOException | OneWireException e) {
				log.append("Failed to stop misson!\n");
			}
		}

	}

}
