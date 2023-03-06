package com.jz.network.security.encoding;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5SaltSample {
  public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String password = "helloworld";
    String salt = "Random Salt";
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    byte[] r = messageDigest.digest((password + salt).getBytes("UTF-8"));
    System.out.println(String.format("%032x", new BigInteger(1, r)));
  }
}
