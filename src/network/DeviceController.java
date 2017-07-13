package network;

import com.google.gson.Gson;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import output.Logger;
import user.Device;
import user.Site;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Justin Havely.
 */
public class DeviceController extends NetworkController {

    public static Device[] getDevices(){
        try {
            // HTTP request with an empty body
            Request request = new Request.Builder()
                    .url(CoCoTempURLs.GET_DEVICES.url())
                    .post(RequestBody.create(null, new byte[0]))
                    .addHeader("authorization", authStringEnc())
                    .addHeader("cache-control", "no-cache")
                    .build();

            // Sends HTTP request
            Response response = client.newCall(request).execute();

            // Checks if the request failed
            if (!response.isSuccessful()) {
                response.body().close();
                return null;
            }

            // Reads the HTTP response (JSON)
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));

            // Uses Google's GSON API to convert the JSON formatted data into
            // Java objects
            Gson gson = new Gson();
            Device[] device = gson.fromJson(reader, Device[].class);

            // Close reader and HTTP request
            reader.close();
            response.body().close();

            return device;
        } catch (IOException e) {
            Logger.writeErrorToLog(e);
            return null;
        }
    }

    public static void addDevice(){

    }

    public static void editDevice(){

    }
}
