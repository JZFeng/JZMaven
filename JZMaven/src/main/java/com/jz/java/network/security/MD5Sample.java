package com.jz.java.network.security;

import sun.plugin2.message.Message;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Sample {
  public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    String original = "MD5java测试";
    byte[] r = toMD5(original.getBytes("UTF-8"));
    System.out.println(String.format("%032x",new BigInteger(1, r)));


  }

  private static byte[] toMD5(byte[] original) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.update(original);
    return messageDigest.digest(original);

  }
}
