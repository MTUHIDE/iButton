package network;

import java.io.File;
import java.io.IOException;

import gui.IButtonApp;
import output.Logger;

public class Upload {
	
	public static boolean uploadFile(Site site, File file) { //TODO change to Okhttpclient and add description
		String charset = "UTF-8";
		try {
			
			MultipartUtility multipart = new MultipartUtility(CoCoTempURLs.UPLOAD_URL.url(), charset, IButtonApp.user, IButtonApp.pass);

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
