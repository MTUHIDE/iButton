package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.MissionContainer;

import handlers.DeviceHandler;
import handlers.MissionHandler;
import output.DataFile;

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
		try {
			DeviceHandler.getAdapter();
			misson.log.append("Found Adapter!\n");
		} catch (OneWireException e) {
			misson.log.append("Could not Find Adapter!\n");
		}

	}

	/**
	 * Listens for button clicks.
	 */
	@Override
	public void actionPerformed(ActionEvent a) {
		DSPortAdapter adapter = null;
		MissionContainer ms = null;

		try {
			adapter = DeviceHandler.getAdapter();
			ms = MissionHandler.getMissionContainer(adapter);
		} catch (OneWireException e) {
			e.printStackTrace();
		}

		if (a.getSource() == startMission) {
			log.append("Starting misson!\n");
			try {
				MissionHandler.startMission(adapter, ms);
			} catch (OneWireException e) {
				e.printStackTrace();
			}
		}
		if (a.getSource() == stopMission) {
			log.append("Stoping misson!\n");
			try {
				MissionHandler.stopMission(adapter, ms);
			} catch (OneWireException e) {
				e.printStackTrace();
			}
		}
		if (a.getSource() == uploadData) {
			log.append("Loading Mission...\n");
			try {
				log.append("Writting to file...\n");
				try { //TODO Should be ran on a new thread so the GUI does not freeze.
					new DataFile(DeviceHandler.getAddress(), MissionHandler.getMissonTemperatureData(adapter, ms))
							.writeDataFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				log.append("Done!\n");
			} catch (OneWireException e) {
				e.printStackTrace();
			}
		}

	}

}
