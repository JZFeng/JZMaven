package com.jz.java.network.security;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Demo {
  public static void main(String[] args) throws UnsupportedEncodingException {
    String original = "Hello 编码测试";
    String s = Base64.getUrlEncoder().withoutPadding().encodeToString(original.getBytes("UTF-8"));
    System.out.println("After encoding : " + s);
    byte[]  afterDecoding = Base64.getUrlDecoder().decode(s);
    System.out.println(new String(afterDecoding, "UTF-8"));

  }
}
