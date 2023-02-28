package com.example.convert;




import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Slf4j
public class SignatureGenerator {



    static public String getSignature(String text) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        // Load private key from file or other source

        byte[] data = text.getBytes(StandardCharsets.UTF_8);

        // Generate key pair
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        String publicKey = keyPair.getPublic().toString();
        String privateKey = keyPair.getPrivate().toString();
        log.info("pubic key:++++++++++");
        log.info("{}", publicKey);
        log.info("public key:+++++++++");

        log.info("private key:----");
        log.info("{}", privateKey);
        log.info("private key:----");

        // Sign data
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        signature.update(data);
        byte[] signedData = signature.sign();

        // Encode signed data as base64
        String encodedData = java.util.Base64.getEncoder().encodeToString(signedData);
        return encodedData;



    }

    /*public void verifySignature(String encodedData){

        // Decode base64 string and verify signature
        byte[] decodedData = java.util.Base64.getDecoder().decode(encodedData);
        signature.initVerify(keyPair.getPublic());
        signature.update(data);
        boolean verified = signature.verify(decodedData);
        System.out.println("Signature verified: " + verified);
    }*/
}
