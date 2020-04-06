package user;


import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.OneWireContainer;
import ibutton.IButtonHandler;
import network.DeviceController;
import network.SiteController;
import output.Logger;

import java.util.*;

/**
 * Created by jmh62 on 7/10/2017.
 */
public class User {
    public String username;
    public String password;

    private List<Device> devices = new ArrayList<>();
    private List<Site> sites = new ArrayList<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Site> getSites() {
        sites = Arrays.asList(SiteController.getSites());
        return sites;
    }

    public Site getSite(String id) {
        sites = Arrays.asList(SiteController.getSites());
        for (Site s : sites) {
            if(s.id.equals(id)){
                return s;
            }
        }
        return null;
    }

    public List<Device> getDevices() {
        devices.clear();
        devices.addAll(Arrays.asList(DeviceController.getDevices()));

        checkForNewDevices(devices);

        return devices;
    }

    private void checkForNewDevices(List<Device> devices){
        try {
            List<OneWireContainer> iButtons = IButtonHandler.getIButtons(IButtonHandler.DEVICE_DEFAULT_NAMES, IButtonHandler.ADAPTER_DEFAULT_NAME);
            for(OneWireContainer ibutton : iButtons){
                boolean in = false;
                for (Device device: devices) {
                    if(ibutton.getAddressAsString().equals(device.manufacture_num) && device.deviceType.equals("iButton")){
                        device.iButton = ibutton;
                        in = true;
                    }
                }
                if(!in){
                    devices.add(new Device(ibutton));
                   // DeviceController.addDevice("", "iButton", ibutton.getAddressAsString());
                }
            }

        } catch (OneWireException e){
            Logger.writeErrorToLog(e);
        }
    }

}
