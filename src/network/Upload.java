package network;

import java.io.File;
import java.io.IOException;

import gui.IButtonApp;
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
	 * @param site The site the temperature reading came from.
	 * @param file The CSV formated TempData file. 
	 * @return True if upload was successful. False if not.
	 */
	public static boolean uploadFile(Site site, File file) { // TODO change to
																// Okhttpclient
																// and add
																// description
		String charset = "UTF-8";
		try {

			MultipartUtility multipart = new MultipartUtility(CoCoTempURLs.UPLOAD_URL.url(), charset, IButtonApp.user,
					IButtonApp.pass);

			multipart.addFormField("siteID", site.id);
			multipart.addFormField("description", "Test upload");

			multipart.addFilePart("csvData", file);

			multipart.finish();
			return true;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return false;
		}
	}

}
