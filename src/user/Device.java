package user;

import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;

/**
 * Created by jmh62 on 7/10/2017.
 */
public class Device {

    public String id, manufacture_num, type, siteID;

    public DSPortAdapter adapter;
    public OneWireContainer iButton;

    /**
     * Creates a new iButton iButton and adapter pair.
     *
     * @param adapter
     *            The adapter the iButton iButton is on.
     * @param iButton
     *            The iButton iButton.
     */
    public Device(DSPortAdapter adapter, OneWireContainer iButton) {
        this.adapter = adapter;
        this.iButton = iButton;
    }

    /**
     * Gets the address of the iButton. This is a 16 char unique identification
     * number for the iButton.
     *
     * @return Text version the address for the iButton.
     */
    public String getAddress() {
        return iButton.getAddressAsString();
    }

    /**
     * @return The iButton and adapter information in string form.
     */
    @Override
    public String toString() {
        return type + ": " + manufacture_num;

    }
}
