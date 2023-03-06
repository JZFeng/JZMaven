package com.jz.network.security.encoding;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Sample {
  public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String original = "MD5摘要算法测试";
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    byte[] r = messageDigest.digest(original.getBytes("UTF-8"));
    System.out.println(String.format("%o",new BigInteger(1, r)));
    System.out.println(String.format("%d",new BigInteger(1, r)));
    System.out.println(String.format("%x",new BigInteger(1, r)));
  }
}
