package com.jz.java.network.security.cypher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AES_ECB {

  private static final String CYPER_NAME = "AES/ECB/PKCS5Padding";

  public static void main(String[] args) throws UnsupportedEncodingException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
    String message = "Hello, world! encrypted using AES!";
    System.out.println("Original Message : " + message);
    byte[] input = message.getBytes("UTF-8");
    byte[] key = "1234567890abcdef".getBytes("UTF-8");
    byte[] encripted = encrypt(key,input);
    System.out.println("Encrypted string: " + Base64.getEncoder().encodeToString(encripted));
    byte[] decrpted = decrypt(key, encripted);
    System.out.println("Decrypted data: " + new String(decrpted, "UTF-8"));

  }

  private static byte[] encrypt(byte[] key, byte[] input) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
    Cipher cipher = Cipher.getInstance(CYPER_NAME);
    SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
    return cipher.doFinal(input);
  }

  private static byte[] decrypt(byte[] key, byte[] input) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance(CYPER_NAME);
    SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    return cipher.doFinal(input);
  }
}
