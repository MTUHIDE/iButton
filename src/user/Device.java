package user;

import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 * Created by jmh62 on 7/10/2017.
 */
public class Device {

    public String id, manufacture_num, deviceType, siteID, shelterType;

    public OneWireContainer iButton;

    /**
     * Creates a new iButton device.
     *
     * @param iButton
     *            The iButton iButton.
     */
    public Device(OneWireContainer iButton) {
        this.iButton = iButton;
        deviceType = "iButton";
        shelterType = "No Shield";
        manufacture_num = iButton.getAddressAsString();
        siteID = "";
    }

    /**
     * @return The iButton and adapter information in string form.
     */
    @Override
    public String toString() {
        return deviceType + ": " + manufacture_num;

    }

}
