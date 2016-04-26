package ie.lockard.security;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * Created by dylan on 26/04/16.
 * Source belongs to Lockard
 */
public class testRun {
    public static void main(String[] args) {
        Random r = new SecureRandom();
        byte[] b = new byte[16];
        r.nextBytes(b);
        String s = Base64.getEncoder().encodeToString(b);
        System.out.println(s);
    }
}
