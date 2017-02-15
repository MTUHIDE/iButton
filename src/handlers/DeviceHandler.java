package handlers;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.adapter.OneWireIOException;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 * This class handles communication with the iButton and the iButton adapter.
 * 
 * @author Justin Havely
 *
 */
public class DeviceHandler {

	/**
	 * Gets the default iButton adapter that is plugged into the computer.
	 * 
	 * @return The default adapter.
	 * @throws OneWireIOException
	 *             If no adapter could be found.
	 * @throws OneWireException
	 *             If no adapter could be found.
	 */
	public static DSPortAdapter getAdapter() throws OneWireIOException, OneWireException {
		// Only work if default adapter is the current adapter. This is set in
		// %user%/.OneWireViewer/onewireviewer.properties
		DSPortAdapter adapter = null;
		adapter = OneWireAccessProvider.getDefaultAdapter();
		return adapter;
	}

	/**
	 * Get the containers for the iButton named "Thermochron8k" also known as
	 * DS1923.
	 * 
	 * @return Null if no iButton named "Thermochron8k" is found. Else, the
	 *         containers for the iButton.
	 * @throws OneWireIOException
	 *             If no adapter could be found.
	 * @throws OneWireException
	 *             If no adapter could be found.
	 */
	public static OneWireContainer getDS1923() throws OneWireIOException, OneWireException {
		DSPortAdapter adapter = getAdapter();
		boolean device = adapter.findFirstDevice();

		while (device) {
			OneWireContainer owc = adapter.getDeviceContainer();
			device = adapter.findNextDevice();
			if (owc.getAlternateNames() == "Thermochron8k") {
				return owc;
			}

		}
		return null;
	}

	/**
	 * Gets the address of the iButton. This is a 16 char unique identification
	 * number for the iButton.
	 * 
	 * @return Text version the address for the iButton.
	 */
	public static String getAddress() {
		try {
			return getDS1923().getAddressAsString();
		} catch (OneWireException e) {
			e.printStackTrace();
		}
		return null;
	}

}
