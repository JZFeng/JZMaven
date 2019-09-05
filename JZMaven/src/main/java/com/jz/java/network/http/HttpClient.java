package com.jz.java.network.http;

import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClient {

  public static void main(String[] args) throws IOException {
    System.out.println(get("https://www.douban.com"));
    Map<String, String> postMap = new HashMap<>();
    postMap.put("form_email", "test");
    postMap.put("form_password", "password");


    System.out.println(post("https://www.douban.com/accounts/login", "application/x-www-form-urlencoded", toFormData(postMap)));

  }

  private static Response get(String theUrl) throws IOException {
    System.out.println("GET: " + theUrl);
    URL url;
    HttpURLConnection conn = null;
    StringBuilder sb = new StringBuilder();
    try {
      url = new URL(theUrl);
      conn = (HttpURLConnection) url.openConnection();
      conn.connect();

      InputStream in = conn.getInputStream();


      BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
      String line = br.readLine();
      while (line != null && line.length() > 0) {
        sb.append(line).append("\n");
        line = br.readLine();
      }
    } finally {
      if(conn != null) {
        conn.disconnect();
      }
    }


    return new Response(conn.getResponseCode(), sb.toString().getBytes());
  }

  private static Response post(String theUrl, String contentType, String contentData) throws IOException {
    System.out.println("POST: " + theUrl);
    HttpURLConnection conn = null;
    try {
      URL url = new URL(theUrl);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      byte[] postdata = contentData.getBytes(StandardCharsets.UTF_8);
      conn.setRequestProperty("Content-Type", contentType);
      conn.setRequestProperty("Content-length", String.valueOf(postdata.length));
      OutputStream out = conn.getOutputStream();
      out.write(postdata);

      ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
      InputStream in = conn.getInputStream();
      byte[] buffer = new byte[1024];
      while (true) {
        int n = in.read(buffer);
        if (n == -1) {
          break;
        }
        responseBuffer.write(buffer, 0, n);
      }

      return new Response(conn.getResponseCode(), responseBuffer.toByteArray());


    } finally {
      if(conn != null) {
        conn.disconnect();
      }
    }


  }


  private static String toFormData(Map<String, String> map) throws IOException {
    List<String> list = new ArrayList<>(map.size());
    for (String key : map.keySet()) {
      list.add(key + "=" + URLEncoder.encode(map.get(key), "UTF-8"));
    }
    return String.join("&", list);
  }
}


class Response {
  private int code;

  private byte[] data;

  Response(int code, byte[] data) {
    this.code = code;
    this.data = data;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(1024);
    sb.append(code).append("\n");
    String s = new String(data, StandardCharsets.UTF_8);
    if (s.length() < 1024) {
      sb.append(s);
    } else {
      sb.append(s.substring(0, 1024)).append("\n...");
    }

    return sb.toString();
  }

}

