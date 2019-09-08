package com.jz.java.network.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Sample {
  public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    String original = "Java摘要算法测试";

    byte[] result = digest("SHA-1", original.getBytes("UTF-8"));
    int length = result.length;
    System.out.println(String.format("%0" + length * 2 + "x",new BigInteger(1, result)) );
  }

  private static byte[] digest(String name, byte[] input) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance(name);
    return messageDigest.digest(input);
  }

}
