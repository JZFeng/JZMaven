package com.jz;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.HttpHeaders.USER_AGENT;

public class Util {

  private static Map<String, String> map = new HashMap<>();

  static {
    map.put("prod", "https://www.ebay.com");
    map.put("preprod", "https://www.latest.ebay.com");
    map.put("staging", "https://www.qa.ebay.com");
  }

  public static void main(String[] args) throws IOException {
    String source = "abcdefg";
    String target = "bcd";
    System.out.println(strStrII(source, target));

/*
    final int a = 0b111;
    final double TAX_RATE = 0.2d;
    System.out.println(TAX_RATE);
    long l = 9000_000_000_000L;
    System.out.println(Long.toHexString(l));
    System.out.println(Long.toBinaryString(l));*/
  }


  public static boolean isClassPresent(String name) {
    try {
      Class.forName(name);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }

  private static String[] reverseArray(String[] Array) {
    return null;
  }


  public static void selectSort(int[] a) {

  }

  public static void insertSort(int[] source) {

  }

  public static int kTimes(int[] nums, int k) {
    Arrays.sort(nums);
    int res = -1;
    int i = 0;

    return res;
  }


  public static void getHttpResponse() throws IOException {
    String url = "https://www.qa.ebay.com/itm/220015975602";

    HttpClient client = HttpClientBuilder.create().build();
    HttpGet request = new HttpGet(url);

    // add request header
    request.addHeader("User-Agent", USER_AGENT);
    HttpResponse response = client.execute(request);

    String rlogid = response.getFirstHeader("RlogId").getValue();

    System.out.println(rlogid);

    System.out.println("Response Code : "
        + response.getStatusLine().getStatusCode());

    BufferedReader rd = new BufferedReader(
        new InputStreamReader(response.getEntity().getContent()));

    StringBuffer result = new StringBuffer();
    String line = "";
    while ((line = rd.readLine()) != null) {
      result.append(line);
    }
  }


  public static int strStr(String source, String target) {
    if (source == null || target == null) {
      return -1;
    }

    for (int i = 0; i < source.length() - target.length(); i++) {
      int j = 0;
      for (; j < target.length(); j++) {
        if (source.charAt(i + j) != target.charAt(j)) {
          break;
        }
      }

      if (j == target.length()) {
        return i;
      }
    }

    return -1;
  }

  public static int strStrII(String source, String target) {
    if (source == null || target == null || source.length() < target.length()) {
      return -1;
    }
    if (target.length() == 0 && source.length() >= 0) {
      return 0;
    }

    int BASE = Integer.MAX_VALUE / 31;
    long hashcode_target = 0;
    for (int i = 0; i < target.length(); i++) {
      hashcode_target = (hashcode_target * 31 % BASE + target.charAt(i)) % BASE;
    }

    int power = 1;
    for (int i = 0; i < target.length(); i++) {
      power = (power * 31 % BASE) % BASE;
    }

    long hashcode_source = 0;
    int target_length = target.length();

    for(int i = 0 ; i < source.length(); i++) {
      hashcode_source =  ( hashcode_source * 31 + source.charAt(i) ) % BASE;
      if( i < target_length - 1) {
        continue;
      }

      if(i >= target_length) {
        hashcode_source = (hashcode_source - power * source.charAt(i - target_length) % BASE) % BASE;
        if (hashcode_source < 0 ) {
          hashcode_source = (hashcode_source + BASE) % BASE;
        }
      }

      if(hashcode_source == hashcode_target) {
        if (source.substring(i - target_length + 1, i + 1 ).equals(target)) {
          return i - target_length + 1;
        }
      }

    }

    return -1;
  }
}