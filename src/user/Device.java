package user;

import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 * Created by jmh62 on 7/10/2017.
 */
public class Device {

    public String id, manufacture_num, type, siteID;

    public OneWireContainer iButton;

    /**
     * Creates a new iButton device.
     *
     * @param iButton
     *            The iButton iButton.
     */
    public Device(OneWireContainer iButton) {
        this.iButton = iButton;
        type = "iButton";
        manufacture_num = iButton.getAddressAsString();
        siteID = "";
    }

    /**
     * @return The iButton and adapter information in string form.
     */
    @Override
    public String toString() {
        return type + ": " + manufacture_num;

    }

}
