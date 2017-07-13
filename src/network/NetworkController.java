package network;

import okhttp3.OkHttpClient;
import user.User;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by Justin Havely
 */
public class NetworkController {
    public static OkHttpClient client = new OkHttpClient();

    public static User currentLoggedInUser;

    /**
     * This method is used for basic HTTP authentication. Uses the currently
     * login user for the credentials.
     *
     * @return A 64 bit encoded string used for basic authentication for HTTP
     *         requests.
     */
    public static String authStringEnc(String username, String password) {
        String authString = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
    }

    public static String authStringEnc() {
        String authString = currentLoggedInUser.username + ":" + currentLoggedInUser.password;
        return "Basic " + Base64.getEncoder().encodeToString(authString.getBytes(StandardCharsets.UTF_8));
    }

}
