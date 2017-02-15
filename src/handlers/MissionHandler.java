package handlers;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.MissionContainer;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 * This class handles communication with the iButton that deals with missions.
 * 
 * @author Justin Havely
 *
 */
public class MissionHandler {

	/**
	 * Used to get the mission container of the iButton. This container is
	 * require to load mission related data.
	 * 
	 * @param adapter
	 *            The adapter the iButton is plugged into.
	 * @return Null if no mission container could be found.
	 * @throws OneWireIOException
	 *             If no iButton could be found.
	 * @throws OneWireException
	 *             If no iButton could be found.
	 */
	public static MissionContainer getMissionContainer(DSPortAdapter adapter)
			throws OneWireIOException, OneWireException {
		boolean device = adapter.findFirstDevice();

		while (device) {
			OneWireContainer owc = adapter.getDeviceContainer();
			device = adapter.findNextDevice();
			MissionContainer container;
			try {
				container = (MissionContainer) owc;
				return container;
			} catch (Exception e) {
				continue;
			}

		}
		return null;
	}

	/**
	 * Gets the temperatures and time sample for the current mission.
	 * 
	 * @param adapter
	 *            The adapter the iButton is connected to.
	 * @param container
	 *            The mission container of the iButton.
	 * @return Null if no mission is running. Else, temperatures and time
	 *         samples.
	 * @throws OneWireException
	 *             If the iButton could not be reached.
	 */
	public static MissionSamples getMissonTemperatureData(DSPortAdapter adapter, MissionContainer container)
			throws OneWireException {
		adapter.beginExclusive(true);

		if (!container.isMissionRunning()) {
			adapter.endExclusive();
			return null;
		}

		container.loadMissionResults();
		int numOfSamples = container.getMissionSampleCount(0);
		MissionSamples ms = new MissionSamples(numOfSamples);

		for (int i = 0; i < numOfSamples; i++) {
			ms.addSample(container.getMissionSample(0, i), container.getMissionSampleTimeStamp(0, i));
		}

		adapter.endExclusive();

		return ms;
	}

	/**
	 * Stops the current mission.
	 * 
	 * @param adapter
	 *            The adapter the iButton is connected to.
	 * @param container
	 *            The mission container of the iButton.
	 * @return True if the mission was stop. False if the mission was not stop.
	 * @throws OneWireException
	 *             If the iButton could not be reached.
	 */
	public static boolean stopMission(DSPortAdapter adapter, MissionContainer container) throws OneWireException {
		adapter.beginExclusive(true);
		if (container.isMissionRunning()) {
			container.stopMission();
			adapter.endExclusive();
			return true;
		}
		adapter.endExclusive();
		return false;
	}

	/**
	 * Starts a new mission and clears old mission samples. The iButton time is
	 * set to UTC time using a NTP server. Sets sample rate to one hour and Roll
	 * over to true.
	 * 
	 * @param adapter
	 *            The adapter the iButton is connected to.
	 * @param container
	 *            The mission container of the iButton.
	 * @return True if the mission was started. False if the mission was not
	 *         started.
	 * @throws OneWireException
	 *             If the iButton could not be reached.
	 */
	public static boolean startMission(DSPortAdapter adapter, MissionContainer container) throws OneWireException {
		adapter.beginExclusive(true);
		if (container.isMissionRunning()) {
			if (!stopMission(adapter, container)) {
				return false;
			}
		}
		ClockHandler.setClock(adapter, container);
		container.clearMissionResults();
		container.setMissionResolution(0, .5);
		container.startNewMission(60 * 60, 0, true, false, new boolean[] { true, false });
		adapter.endExclusive();

		return true;
	}

}
