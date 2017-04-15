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

public class Site {

	public String id, siteName, siteDescription;
	public float siteLatitude, siteLongitude;
	public DeviceHandler device;

	private static OkHttpClient client = new OkHttpClient();

	public String toString() {
		return siteName;
	}

	public String getInfo() {
		return "Site ID: " + id + "\nSite Name: " + siteName + "\nDescription: " + siteDescription + "\nLocation: "
				+ siteLatitude + ":" + siteLongitude + "\niButton: " + getDevice();
	}

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

	private static String authStringEnc() {
		String authString = IButtonApp.user + ":" + IButtonApp.pass;
		String encodeString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
		return new String(encodeString);
	}

	public static Site[] getSites() {
		try {

			Request request = new Request.Builder().url(CoCoTempURLs.GET_SITES.url())
					.post(RequestBody.create(null, new byte[0]))
					.addHeader("authorization", "Basic " + authStringEnc() + "==")
					.addHeader("cache-control", "no-cache").build();

			Response response = client.newCall(request).execute();

			if (!response.isSuccessful()) {
				return null;
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

			Gson gson = new Gson();
			Site[] sites = gson.fromJson(reader, Site[].class);

			reader.close();
			return sites;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}
	}

	public static Response editSite(String siteid, String siteName, String siteLat, String siteLon,
			String description) {
		try {

			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

			RequestBody body = RequestBody.create(mediaType,
					"id=" + siteid + "&siteName=" + siteName.replaceAll(" ", "%20") + "&siteLatitude=" + siteLat
							+ "&siteLongitude=" + siteLon + "&siteDescription=" + description.replaceAll(" ", "%20"));

			Request request = new Request.Builder().url(CoCoTempURLs.EDIT_SITE.url()).post(body)
					.addHeader("content-type", "application/x-www-form-urlencoded")
					.addHeader("authorization", "Basic " + authStringEnc() + "==")
					.addHeader("cache-control", "no-cache").build();

			return client.newCall(request).execute();
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

	public static Response newSite(String siteName, String latitude, String longitude, String description) {
		try {

			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

			RequestBody body = RequestBody.create(mediaType,
					"siteName=" + siteName.replaceAll(" ", "%20") + "&siteLatitude=" + latitude + "&siteLongitude="
							+ longitude + "&siteDescription=" + description.replaceAll(" ", "%20"));

			Request request = new Request.Builder().url(CoCoTempURLs.NEW_SITE.url()).post(body)
					.addHeader("authorization", "Basic " + authStringEnc() + "==")
					.addHeader("content-type", "application/x-www-form-urlencoded")
					.addHeader("cache-control", "no-cache").build();

			return client.newCall(request).execute();
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

}
