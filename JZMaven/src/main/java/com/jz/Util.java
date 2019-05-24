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
    String target = "abcd";
    System.out.println(strStrII(source, target));
  }


  public static boolean isClassPresent(String name) {
    try {
      Class.forName(name);
      return true;
    } catch (ClassNotFoundException e) {
      return false;
    }
  }


  public static int strStr(String source, String target) {
    if (source == null || target == null) {
      return -1;
    }
    if (target.length() == 0 && source.length() >= 0) {
      return -1;
    }
    if (target.length() > source.length()) {
      return -1;
    }


    for (int i = 0; i < source.length() - target.length() + 1; i++) {
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
    if (source == null || target == null) {
      return -1;
    }
    if (target.length() == 0 && source.length() >= 0) {
      return -1;
    }
    if (target.length() > source.length()) {
      return -1;
    }

    int BASE = Integer.MAX_VALUE / 31;
    int length = target.length();

    long hash_target = 0;
    for (int i = 0; i < length; i++) {
      hash_target = (hash_target * 31 + target.charAt(i)) % BASE;
    }

    int power = 1;
    for (int i = 0; i < length; i++) {
      power = (power * 31) % BASE;
    }

    long hash_source = 0;
    for (int i = 0; i < source.length(); i++) {
      hash_source = (hash_source * 31 + source.charAt(i)) % BASE;
      if (i < length - 1) {
        continue;
      }

      if (i >= length) {
        hash_source = (hash_source - power * source.charAt(i - length)) % BASE;
        if (hash_source < 0) {
          hash_source = hash_source + BASE;
        }
      }

      if (hash_source == hash_target) {
        if (source.substring(i - length + 1, i + 1).equals(target)) {
          return i - length + 1;
        }
      }

    }

    return -1;
  }


}