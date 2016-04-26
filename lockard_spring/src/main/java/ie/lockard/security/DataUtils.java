package ie.lockard.security;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

/**
 * Created by dylan on 24/04/16.
 * Source belongs to Lockard
 */
public class DataUtils {


    public static boolean checkHash(String hash, String password) {
        //TODO Implement @William
        return true;
    }

    public static String getHash(String password) {
        //TODO Implement @William
        return password + " - hash";
    }

    public static String getToken() {
        byte[] byteToken = new byte[16];
        new SecureRandom().nextBytes(byteToken);
        return Base64.getEncoder()
                .encodeToString(byteToken)
                .substring(0,16);
    }
}
