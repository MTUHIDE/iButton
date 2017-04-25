package handlers;

import java.util.ArrayList;
import java.util.List;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 * This class handles communication with the iButton devices and adapters.
 * 
 * @author Justin Havely
 *
 */
public class DeviceHandler {
	public static final String adapterDefaultName = "{DS9490}";
	public static final String deviceDefaultName = "Thermochron8k";

	public DSPortAdapter adapter;
	public OneWireContainer device;

	/**
	 * Creates a new iButton device and adapter pair.
	 * 
	 * @param adapter
	 *            The adapter the iButton device is on.
	 * @param device
	 *            The iButton device.
	 */
	private DeviceHandler(DSPortAdapter adapter, OneWireContainer device) {
		this.adapter = adapter;
		this.device = device;
	}

	/**
	 * Gets the adapters that are connected through the USB ports.
	 * 
	 * @return The a list of adapters.
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
	 * Gets all of the iButton devices connected through a port type.
	 * 
	 * @return Null if no iButton devices are found. Else, a list of all found iButton
	 *         devices will be return.
	 * @throws OneWireException
	 *             If no iButton device could be found.
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
	 * @param device
	 *            The iButton device to get the address from.
	 * 
	 * @return String representation of the address for the iButton.
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

	/**
	 * @return The device and adapter information in string form.
	 */
	public String toString() {
		return device + ": " + adapter;

	}

}