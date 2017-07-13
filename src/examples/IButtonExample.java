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
 * Demos how to communicate with a iButton iButton.
 * 
 * @author Justin Havely
 *
 */
public class IButtonExample extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JTextArea log = new JTextArea(10, 40); // Displays helpful
													// information
	private JButton startMission = new JButton("Start Mission");
	private JButton stopMission = new JButton("Stop Mission");
	private JButton readData = new JButton("Read Data");

	private List<OneWireContainer> devices; // All connected iButton devices
	private OneWireContainer activeDevice; // The current iButton iButton being read

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
		IButtonExample dc = new IButtonExample();
		dc.load();
	}

	/**
	 * Searches all ports for the default adapters and devices then sets the
	 * first one found as the active iButton.
	 */
	private void load() {
		try {
			devices = IButtonHandler.getIButtons(IButtonHandler.deviceDefaultName, IButtonHandler.adapterDefaultName);
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
		MissionContainer ms = (MissionContainer) activeDevice;
		// Starts a new mission with default settings
		if (a.getSource() == startMission) {
			log.append("Starting mission\n");
			try {
				MissionHandler.startMission(activeDevice.getAdapter(), ms);
			} catch (OneWireException e) {
				log.append("Failed to start mission!\n");
			}
		}
		// Stops the current mission
		if (a.getSource() == stopMission) {
			log.append("Stop mission\n");
			try {
				MissionHandler.stopMission(activeDevice.getAdapter(), ms);
			} catch (OneWireException e) {
				log.append("Failed to stop mission!\n");
			}
		}
		// Writes mission samples into the data folder
		// (C://user/AppData/Roaming/iButtonData)
		if (a.getSource() == readData) {
			try {
				log.append("Loading Mission...\n");
				MissionSamples samples = MissionHandler.getMissonTemperatureData(activeDevice.getAdapter(), ms);
				log.append("Writing to file...\n");
				new TemperatureData(activeDevice.getAddressAsString(), samples).writeDataFile();
				log.append("Done!\n");
			} catch (IOException | OneWireException e) {
				log.append("Failed to stop mission!\n");
			}
		}

	}

}
