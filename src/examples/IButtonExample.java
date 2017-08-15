package examples;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import com.dalsemi.onewire.container.OneWireContainer;
import ibutton.IButtonHandler;
import ibutton.MissionHandler;
import ibutton.MissionSamples;
import output.TemperatureData;

/**
 * Demos how to communicate with an iButton.
 * 
 * @author Justin Havely
 *
 */
public class IButtonExample extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextArea log = new JTextArea(10, 40); // Displays helpful information
	private JButton startMission = new JButton("Start Mission");
	private JButton stopMission = new JButton("Stop Mission");
	private JButton readData = new JButton("Read Data");

	private List<OneWireContainer> devices; // All connected iButtons
	private OneWireContainer activeDevice; // The current iButton being read

	/**
	 * Sets up the JFrame and components
	 */
	private IButtonExample() {
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
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
		IButtonExample app = new IButtonExample();
		app.load();
	}

	/**
	 * Searches all ports for the default adapters and devices then sets the
	 * first one found as the active iButton.
	 */
	private void load() {
		try {
			devices = IButtonHandler.getIButtons(IButtonHandler.DEVICE_DEFAULT_NAMES, IButtonHandler.ADAPTER_DEFAULT_NAME);
            if(devices.size() != 0) {
				// Change the value '0' to set a different iButton as active, if multiple iButtons are plugged in
				activeDevice = devices.get(0);

				log.append("Found Adapters\n");
			} else {
				log.append("No iButtons plugged in\n");
			}
		} catch (OneWireException e) {
			log.append("Could not Find Adapters!\n");
		}
	}

	/**
	 * Listens for mouse clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		MissionContainer ms = (MissionContainer) activeDevice;
		// Starts a new mission with default settings
		if (a.getSource() == startMission) {
			try {
				MissionHandler.startMission(activeDevice.getAdapter(), ms);
                log.append("Mission Started\n");
			} catch (OneWireException e) {
				log.append("Failed to start mission!\n");
			}
		}
		// Stops the current mission
		if (a.getSource() == stopMission) {
			try {
				MissionHandler.stopMission(activeDevice.getAdapter(), ms);
                log.append("Mission ended\n");
			} catch (OneWireException e) {
				log.append("Failed to stop mission!\n");
			}
		}
		// Writes mission samples into the data folder(C://Users/{user}/AppData/Roaming/iButtonData)
		if (a.getSource() == readData) {
			try {
				log.append("Loading Mission...\n");
				MissionSamples samples = MissionHandler.getMissionTemperatureData(activeDevice.getAdapter(), ms);
				log.append("Writing to file...\n");
				new TemperatureData(activeDevice.getAddressAsString(), samples).writeDataFile();
				log.append("Done! File location: C://user/AppData/Roaming/iButtonData\n");
			} catch (IOException | OneWireException e) {
				log.append("Failed to read data!\n");
			}
		}

	}

}
