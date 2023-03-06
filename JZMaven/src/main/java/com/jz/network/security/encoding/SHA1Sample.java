package com.jz.network.security.encoding;

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

    String name  = "Bob";
    String password = "ILoveYou";
    double salary = 19800.75;
    String s = String.format("User name : %s; Password: %s; Salary : %f", name, password, salary );
    System.out.println(s);
  }

  private static byte[] digest(String name, byte[] input) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance(name);
    return messageDigest.digest(input);
  }

}
