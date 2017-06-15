package network;

/**
 * All of the URL end points for communicating with the CoCo Temp server.
 * @author Justin Havely
 *
 */
public enum CoCoTempURLs {
	SITE("https://hci-dev.cs.mtu.edu:8103/cocotemp/"),
	REGISTER("https://hci-dev.cs.mtu.edu:8103/cocotemp/register"),
	GET_SITES("https://hci-dev.cs.mtu.edu:8103/cocotemp/dashboard/sites.json"),
	EDIT_SITE("https://hci-dev.cs.mtu.edu:8103/cocotemp/settings/site/update"),
	NEW_SITE("https://hci-dev.cs.mtu.edu:8103/cocotemp/settings/site/update"),
	UPLOAD_URL("https://hci-dev.cs.mtu.edu:8103/cocotemp/upload");

	private String url;

	/**
	 * Creates a new URL.
	 * @param url String representation of the URL
	 */
	CoCoTempURLs(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return String representation of the URL
	 */
	public String url() {
		return url;
	}
	
}
