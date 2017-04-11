package network;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import gui.IButtonApp;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import output.Logger;

/**
 * Uploads the CSV TempData file in the data folder to the server.
 * 
 * @author Justin Havely
 *
 */
public class Upload {

	/**
	 * Uploads the CSV TempData file in the data folder to the server.
	 * 
	 * @param site
	 *            The site the temperature reading came from.
	 * @param file
	 *            The CSV formated TempData file.
	 * @return True if upload was successful. False if not.
	 */
	public static boolean uploadFile(Site site, File file) {
		String authString = IButtonApp.user + ":" + IButtonApp.pass;
		String encodeString = Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
		String authStringEnc = new String(encodeString);

		OkHttpClient client = new OkHttpClient();

		RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("siteID", site.id)
				.addFormDataPart("description", "Test upload")
				.addFormDataPart("csvData", file.getName(), RequestBody.create(MediaType.parse("text/csv"), file))
				.build();

		Request request = new Request.Builder().url(CoCoTempURLs.UPLOAD_URL.url()).post(body)
				.addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
				.addHeader("authorization", "Basic " + authStringEnc + "==").addHeader("cache-control", "no-cache")
				.build();

		try {
			Response response = client.newCall(request).execute();
			return true;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return false;
		}

	}

}
