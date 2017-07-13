package ibutton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;
import user.Device;

/**
 * This class handles communication with the iButton devices and adapters.
 * 
 * @author Justin Havely
 *
 */
public class IButtonHandler {
	public static final String adapterDefaultName = "{DS9490}";
	public static final String deviceDefaultName = "Thermochron8k";

	/**
	 * Gets the adapters that are connected through the USB ports.
	 * 
	 * @return The a list of adapters.
	 */
	public static Set<DSPortAdapter> getAdapters(String adapterName) {
		Set<DSPortAdapter> aps = new HashSet<DSPortAdapter>();

		for (int portNum = 2; portNum <= 15; portNum++) {
			try {
				aps.add(OneWireAccessProvider.getAdapter(adapterName, "USB" + Integer.toString(portNum)));
			} catch (OneWireException e) {
				continue;
			}
		}

		return aps;
	}

	/**
	 * Gets all of the iButtons connected through a port type.
	 * 
	 * @return Null if no iButton devices are found. Else, a list of all found iButton
	 *         devices will be return.
	 * @throws OneWireException
	 *             If no iButton iButton could be found.
	 */
	public static List<OneWireContainer> getIButtons(String name, String adapterName) throws OneWireException {
		List<OneWireContainer> iButtons = new ArrayList<OneWireContainer>();
		Set<DSPortAdapter> aps = getAdapters(adapterName);

		for (DSPortAdapter a : aps) {
			boolean isiButton = a.findFirstDevice();

			while (isiButton) {
				OneWireContainer owc = a.getDeviceContainer();
				if (owc.getAlternateNames() == name) {
					iButtons.add(owc);
					break;
				}
                isiButton = a.findNextDevice();
			}

		}
		return iButtons;
	}

}