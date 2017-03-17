package network;

import handlers.DeviceHandler;

public class Site {
	public String id, siteName, siteDescription;
	public float siteLatitude, siteLongitude;
	public DeviceHandler device;

	public String toString() {
		return siteName;
	}
	
	public String getInfo() {
		return "ID: " + id + "\nDevice Name: " + siteName + "\nDescription: " + siteDescription + "\nLocation: "
				+ siteLatitude + ":" + siteLongitude + "\niButton: " + device;
	}

}
