package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.Gson;

import handlers.DeviceHandler;

public class Site {

	public static final String SITES_URL = "https://cocotemp.herokuapp.com/dashboard/sites.json";
	public static final String NEW_SITE_URL = "https://cocotemp.herokuapp.com/settings/site/update";

	public String id, siteName, siteDescription;
	public float siteLatitude, siteLongitude;
	public DeviceHandler device = null;

	public String toString() {
		return siteName;
	}

	public String getInfo() {
		return "ID: " + id + "\nDevice Name: " + siteName + "\nDescription: " + siteDescription + "\nLocation: "
				+ siteLatitude + ":" + siteLongitude + "\niButton: " + device;
	}

	public static Site[] getSites(String name, String pass) throws IOException {
		InputStream response = Authentication.authentication(name, pass, new URL(SITES_URL));
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));

		Gson gson = new Gson();
		Site[] sites = gson.fromJson(reader, Site[].class);
		reader.close();

		return sites;
	}

	/*
	 * public static boolean editSite(Site site) { String updateURL =
	 * "https://cocotemp.herokuapp.com/settings/site/update?siteID=fa806ad8-a8bb-4524-932b-6c4e22d3bfd6";
	 * 
	 * String urlParameters =
	 * "id=fa806ad8-a8bb-4524-932b-6c4e22d3bfd6&siteName=Test+Site+1&siteLatitude=0.0&siteLongitude=0.0&siteDescription=This+site+was+made+for+testing.";
	 * byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8); int
	 * postDataLength = postData.length;
	 * 
	 * String authString = IButtonApp.user + ":" + IButtonApp.pass; String
	 * encodeString =
	 * Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.
	 * UTF_8)); String authStringEnc = new String(encodeString);
	 * 
	 * try { URL url = new URL(updateURL); HttpURLConnection conn =
	 * (HttpURLConnection) url.openConnection(); conn.setDoOutput(true);
	 * 
	 * conn.setRequestMethod("POST"); conn.setRequestProperty("Content-Type",
	 * "application/x-www-form-urlencoded");
	 * conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
	 * conn.setRequestProperty("Content-Length",
	 * Integer.toString(postDataLength));
	 * 
	 * try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream()))
	 * { wr.write(postData); }
	 * 
	 * System.out.println("Server returned: " + conn.getResponseCode()); return
	 * true; } catch (IOException e) { return false; }
	 * 
	 * }
	 * 
	 * 
	 * public static boolean newSite(String siteName, double latitude, double
	 * longitude, String description) { String charset = "UTF-8";
	 * MultipartUtility multipart; try { multipart = new
	 * MultipartUtility(NEW_SITE_URL, charset, IButtonApp.user,
	 * IButtonApp.pass);
	 * 
	 * multipart.addFormField("siteName", siteName);
	 * multipart.addFormField("siteLatitude", Double.toString(latitude));
	 * multipart.addFormField("siteLongitude", Double.toString(longitude));
	 * multipart.addFormField("siteDescription", description);
	 * 
	 * List<String> response = multipart.finish();
	 * 
	 * System.out.println("SERVER REPLIED:");
	 * 
	 * for (String line : response) { System.out.println(line); } } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * return true;
	 * 
	 * }
	 */

}
