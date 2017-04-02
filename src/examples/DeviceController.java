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
import output.Logger;

/**
 * Demos how to use the handlers to communicate to a iButton in a GUI.
 * Writes data to the data folder located at
 *  C://Users/%user%/AppData/Roaming/iButtonData
 * 
 * @author Justin Havely
 *
 */
public class DeviceController extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextArea log = new JTextArea(10, 40);
	private JButton startMission = new JButton("Start Mission");
	private JButton stopMission = new JButton("Stop Mission");
	private JButton readData = new JButton("Read Data");

	private List<DeviceHandler> devices;
	private DeviceHandler activeDevice;

	/**
	 * Sets up the JFrame
	 */
	public DeviceController() {
		log.setEditable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		startMission.addActionListener(this);
		stopMission.addActionListener(this);
		readData.addActionListener(this);
		add(log);
		add(startMission);
		add(stopMission);
		add(readData);
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
		DeviceController misson = new DeviceController();
		misson.load();
	}

	/**
	 * Searches all ports for the default adapters and devices then sets the
	 * first one found as the active device.
	 */
	public void load() {
		try {
			devices = DeviceHandler.getDevices(DeviceHandler.deviceDefaultName, DeviceHandler.adapterDefaultName);
			activeDevice = devices.get(0); // Change 0 to set a different iButton, if multiple are plugged in.
			log.append("Found Adapters!\n");
		} catch (OneWireException e) {
			log.append("Could not Find Adapters!\n");
		}
	}

	/**
	 * Listens for button clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		MissionContainer ms = (MissionContainer) activeDevice.device;
		// Starts a new mission with default settings.
		if (a.getSource() == startMission) {
			log.append("Starting misson!\n");
			try {
				MissionHandler.startMission(activeDevice.adapter, ms);
			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}
		}
		// Stops the current mission
		if (a.getSource() == stopMission) {
			log.append("Stoping misson!\n");
			try {
				MissionHandler.stopMission(activeDevice.adapter, ms);
			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}
		}
		// Writes mission samples into the data folder
		if (a.getSource() == readData) {
			try {
				log.append("Loading Mission...\n");
				MissionSamples samples = MissionHandler.getMissonTemperatureData(activeDevice.adapter, ms);
				log.append("Writting to file...\n");
				new TempData(activeDevice.getAddress(), samples).writeDataFile();
			} catch (IOException | OneWireException e) {
				Logger.writeErrorToLog(e);
			}
			log.append("Done!\n");
		}

	}

}
