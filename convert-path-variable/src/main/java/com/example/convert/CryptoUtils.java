package com.example.convert;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.IntStream;

public class CryptoUtils {

    private static final int SALT_LENGTH = 16;
    public static final String key = IntStream.concat(
                    IntStream.rangeClosed('a', 'z'),
                    IntStream.rangeClosed('0', '9')
            ).mapToObj(c -> (char) c)
            .toList().toString();


    public static String encrypt(String strToEncrypt) {
        try {
            byte[] salt = generateSalt();
            SecretKeySpec secret = generateSecretKeySpec(key, salt);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(generateInitializationVector());
            cipher.init(Cipher.ENCRYPT_MODE, secret, iv);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));
            byte[] finalByteArray = new byte[SALT_LENGTH + iv.getIV().length + encryptedBytes.length];
            System.arraycopy(salt, 0, finalByteArray, 0, SALT_LENGTH);
            System.arraycopy(iv.getIV(), 0, finalByteArray, SALT_LENGTH, iv.getIV().length);
            System.arraycopy(encryptedBytes, 0, finalByteArray, SALT_LENGTH + iv.getIV().length, encryptedBytes.length);
            return new String(Base64.getUrlEncoder().encode(finalByteArray), StandardCharsets.UTF_8);
//            return Base64.getEncoder().encodeToString(finalByteArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            byte[] decodedByteArray = Base64.getUrlDecoder().decode(strToDecrypt);
            byte[] salt = Arrays.copyOfRange(decodedByteArray, 0, SALT_LENGTH);
            byte[] ivBytes = Arrays.copyOfRange(decodedByteArray, SALT_LENGTH, SALT_LENGTH + 16);
            byte[] encryptedBytes = Arrays.copyOfRange(decodedByteArray, SALT_LENGTH + 16, decodedByteArray.length);
            SecretKeySpec secret = generateSecretKeySpec(key, salt);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            cipher.init(Cipher.DECRYPT_MODE, secret, iv);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    private static SecretKeySpec generateSecretKeySpec(String secretKey, byte[] salt) throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] finalKeyBytes = new byte[keyBytes.length + salt.length];
        System.arraycopy(keyBytes, 0, finalKeyBytes, 0, keyBytes.length);
        System.arraycopy(salt, 0, finalKeyBytes, keyBytes.length, salt.length);
        byte[] hashedBytes = sha256.digest(finalKeyBytes);
        return new SecretKeySpec(hashedBytes, "AES");
    }

    private static byte[] generateInitializationVector() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return iv;
    }

}