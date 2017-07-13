package network;

import okhttp3.*;
import user.User;

import java.io.IOException;

/**
 * Created by Justin Havely
 */
public class LoginController extends NetworkController {
    private static OkHttpClient client = new OkHttpClient();

    public static boolean login(String username, String password) {
        try {
            Request request = new Request.Builder()
                    .url(CoCoTempURLs.DASHBOARD.url())
                    .get()
                    .addHeader("authorization", authStringEnc(username, password))
                    .addHeader("cache-control", "no-cache")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                response.body().close();
                currentLoggedInUser = new User(username, password);
                return true;
            } else {
                response.body().close();
                return false;
            }
        } catch (IOException e){
            return false;
        }
    }

    public static void logout() {
        currentLoggedInUser = null;
    }

}


