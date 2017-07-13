package user;

import app.IButtonApp;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.OneWireContainer;
import ibutton.IButtonHandler;
import network.DeviceController;
import network.SiteController;

import java.util.*;

/**
 * Created by jmh62 on 7/10/2017.
 */
public class User {
    public String username;
    public String password;


    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Site[] getSites(){
        return SiteController.getSites();
    }

    public List<Device> getDevices() {
        List<Device> devices = new ArrayList<>();
        try {
            for (OneWireContainer i : IButtonHandler.getIButtons(
                    IButtonHandler.deviceDefaultName, IButtonHandler.adapterDefaultName)) {
                devices.add(new Device(i.getAdapter(), i));
            }
        } catch (OneWireException e){

        }

        devices.addAll(Arrays.asList(DeviceController.getDevices()));

        return devices;
    }

}
