package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import com.google.gson.Gson;

import gui.IButtonApp;
import handlers.DeviceHandler;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import output.Logger;
import output.SiteData;

/**
 * This class serves two purposes, handling communication (OkHttpClient API)
 * with the CoCo Temp server and acting as an site object. This class should be
 * split up into two different classes, but thats for another day.
 * 
 * @author Justin Havely
 *
 */
public class Site {

	public String id, siteName, siteDescription;
	public float siteLatitude, siteLongitude;
	public DeviceHandler device;

	private static OkHttpClient client = new OkHttpClient();

	/**
	 * @return The site's name given by the user.
	 */
	public String toString() {
		return siteName;
	}

	/**
	 * 
	 * @return A block of text that contains the site's name, description,
	 *         location, and the iButton assigned to it.
	 */
	public String getInfo() {
		return "Site ID: " + id + "\nSite Name: " + siteName + "\nDescription: " + siteDescription + "\nLocation: "
				+ siteLatitude + ":" + siteLongitude + "\niButton: " + getDevice();
	}

	/**
	 * 
	 * This method is used in the getInfo method as a helper method. (To make
	 * the code look nicer)
	 * 
	 * @return A short description of the iButton assign to this site. it can be
	 *         one of three options: Not assigned, Not plugged in, or the
	 *         address of the iButton if plugged in.
	 */
	private String getDevice() {
		String deviceAddress = SiteData.findSiteBySite(id)[1];

		if (device != null) {
			return device.getAddress();
		} else if (deviceAddress != null && deviceAddress.equals("null")) {
			return "Not assigned";
		} else if (deviceAddress != null && !deviceAddress.equals("null")) {
			return "Not plugged in";
		}

		return null;
	}

	/**
	 * This method is used for basic HTTP authentication. Uses the currently
	 * login user for the credentials.
	 * 
	 * @return A 64 bit encoded string used for basic authentication for HTTP
	 *         requests.
	 */
	private static String authStringEnc() {
		String authString = IButtonApp.user + ":" + IButtonApp.pass;
		String encodeString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
		return new String(encodeString);
	}

	/**
	 * Sends a HTTP request to the CoCo Temp server for the logged in user's
	 * sites.
	 * 
	 * @return A list of sites, or an empty list if the users has none. Null if
	 *         it could not get the user's sites.
	 */
	public static Site[] getSites() {
		try {
			// HTTP request with an empty body
			Request request = new Request.Builder().url(CoCoTempURLs.GET_SITES.url())
					.post(RequestBody.create(null, new byte[0]))
					.addHeader("authorization", "Basic " + authStringEnc() + "==")
					.addHeader("cache-control", "no-cache").build();

			// Sends HTTP request
			Response response = client.newCall(request).execute();

			// Checks if the request failed
			if (!response.isSuccessful()) {
				response.body().close();
				return null;
			}

			// Reads the HTTP response (JSON)
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

			// Uses Google's GSON API to convert the JSON formated data into
			// Java objects
			Gson gson = new Gson();
			Site[] sites = gson.fromJson(reader, Site[].class);

			// Close reader and HTTP request
			reader.close();
			response.body().close();
			return sites;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}
	}

	/**
	 * Sends a HTTP request to the CoCo Temp server for the logged in user to
	 * update a site. Make sure to close the request body!
	 * 
	 * @param siteid
	 *            The site's ID that is being updated.
	 * @param siteName
	 *            The new name of the site.
	 * @param siteLat
	 *            The new latitude of the site.
	 * @param siteLon
	 *            The new longitude of the site.
	 * @param description
	 *            The new description of the site.
	 * @return returns the response of the HTTP request, or null if an error
	 *         occurred.
	 */
	public static Response editSite(String siteid, String siteName, String siteLat, String siteLon,
			String description) {
		try {
			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

			// HTTP request body
			RequestBody body = RequestBody.create(mediaType,
					"id=" + siteid + "&siteName=" + siteName.replaceAll(" ", "%20") + "&siteLatitude=" + siteLat
							+ "&siteLongitude=" + siteLon + "&siteDescription=" + description.replaceAll(" ", "%20"));

			// HTTP request headers and builds the request
			Request request = new Request.Builder().url(CoCoTempURLs.EDIT_SITE.url()).post(body)
					.addHeader("content-type", "application/x-www-form-urlencoded")
					.addHeader("authorization", "Basic " + authStringEnc() + "==")
					.addHeader("cache-control", "no-cache").build();

			// Sends HTTP request
			return client.newCall(request).execute();
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

	/**
	 * Sends a HTTP request to the CoCo Temp server for the logged in user to
	 * add a new site. Make sure to close the request body!
	 * 
	 * 
	 * @param siteName
	 *            The name of the new site.
	 * @param siteLat
	 *            The latitude of the new site.
	 * @param siteLon
	 *            The longitude of the new site.
	 * @param description
	 *            The description of the new site.
	 * @return returns the response of the HTTP request, or null if an error
	 *         occurred.
	 */
	public static Response newSite(String siteName, String latitude, String longitude, String description) {
		try {
			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

			// HTTP request body
			RequestBody body = RequestBody.create(mediaType,
					"siteName=" + siteName.replaceAll(" ", "%20") + "&siteLatitude=" + latitude + "&siteLongitude="
							+ longitude + "&siteDescription=" + description.replaceAll(" ", "%20"));

			// HTTP request headers and builds the request
			Request request = new Request.Builder().url(CoCoTempURLs.NEW_SITE.url()).post(body)
					.addHeader("authorization", "Basic " + authStringEnc() + "==")
					.addHeader("content-type", "application/x-www-form-urlencoded")
					.addHeader("cache-control", "no-cache").build();

			// Sends HTTP request
			return client.newCall(request).execute();
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

}
