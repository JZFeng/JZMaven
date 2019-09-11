package com.jz.java.network.security.encoding;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLDecode {
  public static void main(String[] args) throws UnsupportedEncodingException {
    String original = "URL 参数";
    String s = URLEncoder.encode(original,"UTF-8");
    System.out.println(original + " 编码之后：" + s);
    System.out.println(s + " 解码之后 ：" + URLDecoder.decode(s,"UTF-8"));

  }
}
