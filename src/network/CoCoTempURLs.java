package network;

/**
 * All of the URL end points for communicating with the CoCo Temp server.
 * @author Justin Havely
 *
 */
public enum CoCoTempURLs {
	SITE(""),
	REGISTER("register"),
	GET_SITES("dashboard/sites.json"),
	GET_DEVICES("dashboard/devices.json"),
	EDIT_SITE("settings/site/update"),
	NEW_SITE("settings/site/update"),
    NEW_DEVICE("device/new"),
	EDIT_DEVICE("settings/device"),
	UPLOAD("upload"),
	DASHBOARD("dashboard");

	private String url;

	//(Live) https://hci-dev.cs.mtu.edu:8103/cocotemp/
    //(Local) http://localhost:4040/cocotemp/
	public static final String HOST = "http://localhost:4040/cocotemp/";

	/**
	 * Creates a new URL.
	 * @param page String representation of the URL
	 */
	CoCoTempURLs(String page) {
		this.url = HOST + page;
	}

	/**
	 * 
	 * @return String representation of the URL
	 */
	public String url() {
		return url;
	}
	
}
