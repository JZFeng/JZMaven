package com.jz.java.network.security;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Practice {

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    File f = new File("/Users/jzfeng/Downloads/mysql-connector-java-8.0.17-1.sl15.src.rpm");
    InputStream in = new FileInputStream("/Users/jzfeng/Downloads/mysql-connector-java-8.0.17-1.sl15.src.rpm");
    byte[] buffer = new byte[1024];
    ByteArrayOutputStream bo = new ByteArrayOutputStream();
    int n ;
    while ((n = in.read(buffer)) != -1) {
      bo.write(buffer, 0, n);
    }

    byte[] result = toMD5(bo.toByteArray());
    System.out.println(String.format("%032x", new BigInteger(1, result)));

  }


  private static byte[] toMD5(byte[] input) throws NoSuchAlgorithmException {
    MessageDigest md;
    md = MessageDigest.getInstance("MD5");
    return md.digest(input);
  }

}
