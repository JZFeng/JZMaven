package com.jz.java.network.security;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Hmac {
  public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
    String data = "helloworld";
    String algorithm = "HmacSHA1";
    KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
    SecretKey secretKey = keyGenerator.generateKey();
    byte[] r = toHmac("HmacSHA1", secretKey, data.getBytes("UTF-8"));
    System.out.println(String.format("%0" + (r.length * 2) + "x", new BigInteger(1, r)));

  }

  private static byte[] toHmac(String algorithm, SecretKey secretKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
    Mac mac = Mac.getInstance(algorithm);
    mac.init(secretKey);
    return mac.doFinal(data);
  }
}


