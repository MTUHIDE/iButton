package network;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class Upload {
	public static final String UPLOAD_URL = "https://cocotemp.herokuapp.com/upload";

	public static void uploadFile(Site site, File file) {
		 String charset = "UTF-8";
	 
	        try {
	            MultipartUtility multipart = new MultipartUtility(UPLOAD_URL, charset, "jusbmx","notagoodpass");
	             
	            multipart.addFormField("siteID", site.id);
	            multipart.addFormField("description", "Test upload");
	             
	            multipart.addFilePart("csvData", file);
	 
	            List<String> response = multipart.finish();
	             
	            System.out.println("SERVER REPLIED:");
	             
	            for (String line : response) {
	                System.out.println(line);
	            }
	        } catch (IOException ex) {
	            System.err.println(ex);
	        }

	}

}
