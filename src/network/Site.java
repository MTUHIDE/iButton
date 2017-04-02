package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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

public class Site {

	public String id, siteName, siteDescription;
	public float siteLatitude, siteLongitude;
	public DeviceHandler device = null;

	public String toString() {
		return siteName;
	}

	public String getInfo() {
		return "Site ID: " + id + "\nSite Name: " + siteName + "\nDescription: " + siteDescription + "\nLocation: "
				+ siteLatitude + ":" + siteLongitude + "\niButton: " + device;
	}

	public static Site[] getSites(String name, String pass) { //TODO Change to Okhttpclient and delete auth claass
		try {
			@SuppressWarnings("deprecation")
			InputStream response = Authentication.authentication(name, pass, new URL(CoCoTempURLs.GET_SITES.url()));
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(response));

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

		String authString = IButtonApp.user + ":" + IButtonApp.pass;
		String encodeString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
		String authStringEnc = new String(encodeString);

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

		RequestBody body = RequestBody.create(mediaType,
				"id=" + siteid + "&siteName=" + siteName.replaceAll(" ", "%20") + "&siteLatitude=" + siteLat
						+ "&siteLongitude=" + siteLon + "&siteDescription=" + description.replaceAll(" ", "%20"));

		Request request = new Request.Builder().url(CoCoTempURLs.EDIT_SITE.url()).post(body)
				.addHeader("content-type", "application/x-www-form-urlencoded")
				.addHeader("authorization", "Basic " + authStringEnc + "==").addHeader("cache-control", "no-cache")
				.build();

		try {
			return client.newCall(request).execute();
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

	public static Response newSite(String siteName, String latitude, String longitude, String description) {

		String authString = IButtonApp.user + ":" + IButtonApp.pass;
		String encodeString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
		String authStringEnc = new String(encodeString);

		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

		RequestBody body = RequestBody.create(mediaType,
				"siteName=" + siteName.replaceAll(" ", "%20") + "&siteLatitude=" + latitude + "&siteLongitude="
						+ longitude + "&siteDescription=" + description.replaceAll(" ", "%20"));

		Request request = new Request.Builder().url(CoCoTempURLs.EDIT_SITE.url()).post(body)
				.addHeader("authorization", "Basic " + authStringEnc + "==")
				.addHeader("content-type", "application/x-www-form-urlencoded").addHeader("cache-control", "no-cache")
				.build();

		try {
			return client.newCall(request).execute();
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

}
