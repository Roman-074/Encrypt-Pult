package com.pult.encryption;

import android.annotation.SuppressLint;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

@SuppressLint("NewApi")
public class RSA {

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    public static void generateKeyPair(){
        if (privateKey == null && publicKey == null) {
            try {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(2048);

                KeyPair keyPair = keyGen.generateKeyPair();
                privateKey = keyPair.getPrivate();
                publicKey = keyPair.getPublic();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  static String encryptByPrivateKey(String data, PrivateKey key){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedData = cipher.doFinal(dataBytes);
            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) { e.printStackTrace(); }

        return "empty encrypt data";
    }

    public static String decryptByPublicKey(String encryptData, PublicKey key){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decrypted = cipher.doFinal();
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) { e.printStackTrace(); }

        return "empty decrypt data";
    }

    public static String decryptByPrivateKey(String encryptData, PrivateKey key){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decrypted = cipher.doFinal();
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (Exception e) { e.printStackTrace(); }

        return "empty decrypt data";
    }

    public static PrivateKey getPrivateKey() { return privateKey; }
    public static PublicKey getPublicKey() { return publicKey; }


    //
    public static String encryptBytesByPrivateKey(byte[] dataBytes){
        generateKeyPair();
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedData = cipher.doFinal(dataBytes);
            return Base64.getEncoder().encodeToString(encryptedData);

        } catch (Exception e) { e.printStackTrace(); }

        return "empty encrypt data";
    }

}
