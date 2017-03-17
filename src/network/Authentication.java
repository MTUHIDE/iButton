package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Authentication {

	public static final String SITES_URL = "https://cocotemp.herokuapp.com/dashboard/sites.json";

	public static InputStream authentication(String username, String password, URL url) throws IOException {

		String authString = username + ":" + password;
		String encodeString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
		String authStringEnc = new String(encodeString);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Authorization", "Basic " + authStringEnc);

		InputStream content = (InputStream) connection.getInputStream();
		
		return content;
	}

	public static void printResponse(InputStream response) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}