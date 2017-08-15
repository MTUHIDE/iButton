package ibutton;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.MissionContainer;

/**
 * This class handles communication with the iButton that deals with missions.
 * 
 * @author Justin Havely
 *
 */
public class MissionHandler {

	/**
	 * Gets the temperatures and time samples for the current mission.
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
	public static MissionSamples getMissionTemperatureData(DSPortAdapter adapter, MissionContainer container)
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
	 * Starts a new mission and removes old mission samples. The iButton time is
	 * set to UTC time using a NTP server. Sets sampling rate to one hour and
	 * Roll over to true.
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
