package network;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import output.Logger;
import user.Site;

/**
 * Uploads the CSV TemperatureData file in the data folder
 * (C://user/AppData/Roaming/iButtonData) to the CoCo Temp server.
 * 
 * @author Justin Havely
 *
 */
public class UploadController extends NetworkController {

	/**
	 * Uploads the CSV TemperatureData file in the data folder to the server.
	 * 
	 * @param site
	 *            The siteController that the temperature readings came from.
	 * @param file
	 *            The CSV formatted TemperatureData file.
	 * @return True if upload was successful. False if it was not successful.
	 */
	public static boolean uploadFile(Site site, File file) {
		try {
			// HTTP request body
			RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("siteID", site.id)
					.addFormDataPart("description", "Uploaded from CoCo iTemp App")
					.addFormDataPart("csvData", file.getName(), RequestBody.create(MediaType.parse("text/csv"), file))
					.build();

			// HTTP request headers and builds the request
			Request request = new Request.Builder().url(CoCoTempURLs.UPLOAD.url()).post(body)
					.addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
					.addHeader("authorization", authStringEnc())
					.addHeader("cache-control", "no-cache")
					.build();

			// Sends HTTP request
			Response response = client.newCall(request).execute();

			// Checks if the request failed
			response.body().close();
			return response.isSuccessful();

		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return false;
		}

	}

}
