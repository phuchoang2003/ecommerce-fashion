package org.example.ecommercefashion.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PasswordUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public PasswordUtils() {
    }

    private static byte[] hash(char[] password) {
        String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";
        PBEKeySpec spec = new PBEKeySpec(password, salt.getBytes(), 10000, 256);
        Arrays.fill(password, '\u0000');

        byte[] var4;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            var4 = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException var8) {
            GeneralSecurityException e = var8;
            throw new AssertionError("Error while hashing a password: " + ((GeneralSecurityException) e).getMessage(), e);
        } finally {
            spec.clearPassword();
        }

        return var4;
    }

    public static String encode(String password) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    public static boolean verifyPassword(String providedPassword, String securedPassword) {
        boolean returnValue = false;
        String newSecurePassword = encode(providedPassword);
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        return returnValue;
    }
}