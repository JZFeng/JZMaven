package com.jz.java.regex;

public class ID {
  public static boolean isValidId(String str) {
    if(str == null || str.length() != 18) {
      return false;
    }
    return str.matches("^[1-9]\\d{16}[0-9X]$");
  }
}
