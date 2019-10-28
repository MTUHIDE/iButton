package examples;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.MissionContainer;

import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.container.OneWireContainer21;
import com.dalsemi.onewire.container.OneWireContainer41;
import ibutton.IButtonHandler;
import ibutton.MissionHandler;
import ibutton.MissionSamples;
import output.Logger;
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
	private JScrollPane scrollV = new JScrollPane(log);

	private List<OneWireContainer> devices; // All connected iButtons
	private OneWireContainer21 activeDevice; // The current iButton being read

	/**
	 * Sets up the JFrame and components
	 */
	private IButtonExample() {
		scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setLayout(new FlowLayout());

		log.setEditable(false);
		startMission.addActionListener(this);
		stopMission.addActionListener(this);
		readData.addActionListener(this);

		add(log);
		add(scrollV);
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
				activeDevice = (OneWireContainer21) devices.get(0);

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
		// Starts a new mission with default settings
		if (a.getSource() == startMission) {
			try {
				MissionHandler.startMission(activeDevice);
				log.append("Mission Started\n");
			} catch (OneWireException e) {
				log.append("Failed to start mission!\n");
			}
		}
		// Stops the current mission
		if (a.getSource() == stopMission) {
			try {
				MissionHandler.stopMission(activeDevice);
				log.append("Mission ended\n");
			} catch (OneWireException e) {
				log.append("Failed to stop mission!\n");
			}
		}
		// Writes mission samples into the data folder(C://Users/{user}/AppData/Roaming/iButtonData)
		if (a.getSource() == readData) {

				log.append("Loading Mission...\n");

				byte[] state = null;
				try {
					state = activeDevice.readDevice();
				} catch (OneWireException e) {
					Logger.writeErrorToLog(e);
				}
					try {
						// Gets temperature readings
						byte[] tempLog = activeDevice.getTemperatureLog(state);
						MissionSamples samples = new MissionSamples(tempLog.length);
						Calendar time_stamp = activeDevice.getMissionTimeStamp(state);
						int sample_rate = activeDevice.getSampleRate(state);
						long time = time_stamp.getTime().getTime() + activeDevice.getFirstLogOffset(state);
						char temp_standard = 'C';
						for (int i = 0; i < tempLog.length; i++) {
							samples.addSample(activeDevice.decodeTemperature(tempLog[i]),temp_standard, time);
							time += sample_rate * 60 * 1000;
						}
						if(samples.getLength()>0) {
							log.append("Writing to file...\n");
							new TemperatureData(activeDevice.getAddressAsString(), samples).writeDataFile();
							log.append("Done! File location: C://user/AppData/Roaming/iButtonData\n");
						}
						else{
							log.append("No data available yet\n");
						}
					} catch (IOException | OneWireException e) {
						log.append("Failed to read data!\n");
					}
				}
			}
		}