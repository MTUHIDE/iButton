package network;

import java.io.File;
import java.io.IOException;

import gui.IButtonApp;

public class Upload {
	public static final String UPLOAD_URL = "https://cocotemp.herokuapp.com/upload";

	public static void uploadFile(Site site, File file) {
		String charset = "UTF-8";

		try {
			MultipartUtility multipart = new MultipartUtility(UPLOAD_URL, charset, IButtonApp.user, IButtonApp.pass);

			multipart.addFormField("siteID", site.id);
			multipart.addFormField("description", "Test upload");

			multipart.addFilePart("csvData", file);

			multipart.finish();

		} catch (IOException ex) {
			System.err.println(ex);
		}

	}

}
