package network;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import output.Logger;
import user.Device;

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

    public static boolean addDevice(String siteID, String type, String manufacture_num, String shelter_type){

        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            // HTTP request body
            RequestBody body = RequestBody.create(mediaType,
                    "siteID=" + siteID.replaceAll(" ", "%20") + "&deviceType="
                            + type + "&manufacture_num=" + manufacture_num+"&shelterType="+shelter_type);

            // HTTP request headers and builds the request
            Request request = new Request.Builder().url(CoCoTempURLs.NEW_DEVICE.url()).post(body)
                    .addHeader("authorization", authStringEnc())
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("cache-control", "no-cache").build();

            // Sends HTTP request
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            Logger.writeErrorToLog(e);
            return false;
        }

    }

    public static boolean editDevice(String deviceID, String siteID, String type, String manufacture_num,String shelter_type){
        try {
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            // HTTP request body
            RequestBody body = RequestBody.create(mediaType,
                    "id=" + deviceID + "&siteID=" + siteID + "&deviceType=" + type
                            + "&manufacture_num=" + manufacture_num+"&shelterType="+shelter_type + "&update=update");

            // HTTP request headers and builds the request
            Request request = new Request.Builder().url(CoCoTempURLs.EDIT_DEVICE.url()).post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .addHeader("authorization", authStringEnc())
                    .addHeader("cache-control", "no-cache").build();

            // Sends HTTP request
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            Logger.writeErrorToLog(e);
            return false;
        }
    }
}
