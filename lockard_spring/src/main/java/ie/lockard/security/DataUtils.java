package ie.lockard.security;
<<<<<<< HEAD

import org.apache.tomcat.util.codec.binary.Base64;

=======
import org.apache.commons.codec.binary.Base64;
>>>>>>> 46ca8deadcfa6ea549db53ef6473739ca1f26af6
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
/**
<<<<<<< HEAD
 * Created by William, Dylan on 24/04/16.
=======
 * Created by William on 24/04/16.
>>>>>>> 46ca8deadcfa6ea549db53ef6473739ca1f26af6
 * Source belongs to Lockard
 */
public class DataUtils {

<<<<<<< HEAD
    private static final int iterations = 10 * 1000;
    private static final int saltLen = 16;
=======
    private static final int iterations = 1*1000;
    private static final int saltLen = 32;
>>>>>>> 46ca8deadcfa6ea549db53ef6473739ca1f26af6
    private static final int desiredKeyLen = 256;


    public static boolean checkHash(String hash, String password) {
        String[] saltAndPass = hash.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = hash(password, Base64.decodeBase64(saltAndPass[0]));
        return hashOfInput.equals(saltAndPass[1]);
    }

    public static String getHash(String password) {
        byte[] salt = new byte[0];
        try {
            salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLen);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("no");
        }
<<<<<<< HEAD
// store the salt with the password
=======
        // store the salt with the password
>>>>>>> 46ca8deadcfa6ea549db53ef6473739ca1f26af6
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    private static String hash(String password, byte[] salt) {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");

        SecretKey key = null;
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            key = f.generateSecret(new PBEKeySpec(
<<<<<<< HEAD
                    password.toCharArray(), salt, iterations, desiredKeyLen)
            );
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            System.out.println("no");
        }
        assert key != null;
=======
                            password.toCharArray(), salt, iterations, desiredKeyLen)
            );
        } catch (InvalidKeySpecException e) {
            System.out.println("no");
        } catch (NoSuchAlgorithmException e){
            System.out.println("no");
        }
>>>>>>> 46ca8deadcfa6ea549db53ef6473739ca1f26af6
        return Base64.encodeBase64String(key.getEncoded());
    }

    public static String getToken() {
        byte[] byteToken = new byte[16];
        new SecureRandom().nextBytes(byteToken);
        return java.util.Base64.getEncoder()
                .encodeToString(byteToken)
                .substring(0, 16);
    }
<<<<<<< HEAD
}
=======

 /*   public static void main(String[] args){
        System.out.println(getHash("banana"));
        System.out.println(getHash("banana"));
        System.out.println(getHash("banana"));
        if(checkHash(getHash("banana"),"banana"))
           System.out.println("yes");
        else
            System.out.println("no");
      }*/
}
>>>>>>> 46ca8deadcfa6ea549db53ef6473739ca1f26af6
