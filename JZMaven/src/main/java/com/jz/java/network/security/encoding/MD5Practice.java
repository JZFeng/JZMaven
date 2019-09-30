package com.jz.java.network.security.encoding;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Practice {

  public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
    //caculate md5 of a downloaded file;
    String md5 ;
    try (InputStream in = new FileInputStream("/Users/jzfeng/Downloads/mysql-connector-java-3.0.15-ga.zip")) {
      byte[] buffer = new byte[1024];
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      int n;
      while ((n = in.read(buffer)) != -1) {
        bo.write(buffer, 0, n);
      }

      byte[] result = toMD5(bo.toByteArray());
      md5 = String.format("%032x", new BigInteger(1, result));
      System.out.println(md5);
    }

    //compare md5 with original md5;
    try (InputStream inputStream = new FileInputStream("/Users/jzfeng/Downloads/mysql-connector-java-3.0.15-ga.zip.md5")) {
      byte[] buf = new byte[1024];
      int n;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      while ((n = inputStream.read(buf)) != -1) {
        bos.write(buf, 0, n);
      }
      String s = new String(bos.toByteArray(), "UTF-8");
      System.out.println(md5.equals(s));
    }
  }


  private static byte[] toMD5(byte[] input) throws NoSuchAlgorithmException {
    MessageDigest md;
    md = MessageDigest.getInstance("MD5");
    return md.digest(input);
  }

}
