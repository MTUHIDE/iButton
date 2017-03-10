package network;

import java.io.File;
import java.io.IOException;

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

		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY); //CSV File

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("siteID", site.id, ContentType.DEFAULT_TEXT);
		builder.addPart("csvData", fileBody);
		builder.addTextBody("description", "Test upload", ContentType.DEFAULT_TEXT);

		// Authentication
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("jusbmx", "notagoodpass"); //Test account
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

		HttpPost httppost = new HttpPost(UPLOAD_URL);
		httppost.setEntity(builder.build());
		try {
			HttpResponse response = httpclient.execute(httppost);
			System.out.println(response.getEntity().getContent());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
