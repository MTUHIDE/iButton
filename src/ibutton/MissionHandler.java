package ibutton;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.MissionContainer;
import com.dalsemi.onewire.container.OneWireContainer21;

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
			ms.addSample(container.getMissionSample(0, i), 'C',container.getMissionSampleTimeStamp(0, i));
		}

		adapter.endExclusive();

		return ms;
	}

	/**
	 * Stops the current mission.
	 *
	 * @param container
	 *            The mission container of the iButton.
	 * @return True if the mission was stop. False if the mission was not stop.
	 * @throws OneWireException
	 *             If the iButton could not be reached.
	 */
	public static boolean stopMission(OneWireContainer21 container) throws OneWireException {
			container.disableMission();
			container.clearMemory();
		return true;
	}

	/**
	 * Starts a new mission and removes old mission samples. The iButton time is
	 * set to UTC time using a NTP server. Sets sampling rate to one hour and
	 * Roll over to true.
	 *
	 * @param container
	 *            The mission container of the iButton.
	 * @return True if the mission was started. False if the mission was not
	 *         started.
	 * @throws OneWireException
	 *             If the iButton could not be reached.
	 */
	public static boolean startMission(OneWireContainer21 container) throws OneWireException {
		byte [] state = container.readDevice();
		container.disableMission();
		container.setClock(System.currentTimeMillis(),state);
		container.clearMemory();
		container.enableMission(60);

		return true;
	}

}
