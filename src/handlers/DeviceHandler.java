package handlers;

import java.util.ArrayList;
import java.util.List;

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
	public static final String adapterDefaultName = "{DS9490}";
	public static final String deviceDefaultName = "Thermochron8k";

	public DSPortAdapter adapter;
	public OneWireContainer device;

	private DeviceHandler(DSPortAdapter adapter, OneWireContainer device) {
		this.adapter = adapter;
		this.device = device;
	}
	
	/**
	 * Gets the default iButton adapter that is plugged into the computer.
	 * 
	 * @return The default adapter.
	 * @throws OneWireIOException
	 *             If no adapter could be found.
	 * @throws OneWireException
	 *             If no adapter could be found.
	 */
	public static List<DSPortAdapter> getAdapters(String adapterName) {
		List<DSPortAdapter> aps = new ArrayList<DSPortAdapter>();

		for (int port_type = 0; port_type <= 15; port_type++) {
			try {
				aps.add(OneWireAccessProvider.getAdapter(adapterName, "USB" + Integer.toString(port_type)));
			} catch (OneWireException e) {
				continue;
			}
		}

		return aps;
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
	public static List<DeviceHandler> getDevices(String deviceName, String adapterName) throws OneWireException {
		List<DeviceHandler> devices = new ArrayList<DeviceHandler>();
		List<DSPortAdapter> aps = getAdapters(adapterName);

		for (DSPortAdapter a : aps) {
			boolean device = a.findFirstDevice();

			while (device) {
				OneWireContainer owc = a.getDeviceContainer();
				device = a.findNextDevice();
				if (owc.getAlternateNames() == deviceName) {
					devices.add(new DeviceHandler(a, owc));
				}
			}

		}
		return devices;
	}

	/**
	 * Gets the address of the iButton. This is a 16 char unique identification
	 * number for the iButton.
	 * 
	 * @return Text version the address for the iButton.
	 */
	public static String getAddress(OneWireContainer device) {
		return device.getAddressAsString();
	}

	/**
	 * Gets the address of the iButton. This is a 16 char unique identification
	 * number for the iButton.
	 * 
	 * @return Text version the address for the iButton.
	 */
	public String getAddress() {
		return device.getAddressAsString();
	}

	public String toString(){
		return device + ": " + adapter;
		
	}
	
}