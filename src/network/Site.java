package network;

import handlers.DeviceHandler;

public class Site {
	public String id, deviceName, deviceDescription;
	public float deviceLatitude, deviceLongitude;
	public DeviceHandler device;

	public String toString() {
		return deviceName;
	}
	
	public String getInfo() {
		return "ID: " + id + ", Device Name: " + deviceName + "\nDescription: " + deviceDescription + "\nLocation: "
				+ deviceLatitude + ":" + deviceLongitude + "\niButton: " + device;
	}

}
