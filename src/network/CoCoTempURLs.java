package network;

/**
 * All of the URL end points for communicating with the server.
 * @author Justin Havely
 *
 */
public enum CoCoTempURLs {
	REGISTER("https://hci-dev.cs.mtu.edu:8103/cocotemp/register"),
	SITE("https://hci-dev.cs.mtu.edu:8103/cocotemp/"),
	GET_SITES("https://hci-dev.cs.mtu.edu:8103/cocotemp/dashboard/sites.json"),
	EDIT_SITE("https://hci-dev.cs.mtu.edu:8103/cocotemp/settings/site/update"),
	NEW_SITE("https://hci-dev.cs.mtu.edu:8103/cocotemp/settings/site/update"),
	UPLOAD_URL("https://hci-dev.cs.mtu.edu:8103/cocotemp/upload");

	private String url;

	CoCoTempURLs(String url) {
		this.url = url;
	}

	public String url() {
		return url;
	}
}
