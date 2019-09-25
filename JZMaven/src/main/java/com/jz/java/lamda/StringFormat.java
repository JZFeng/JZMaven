package com.jz.java.lamda;

import java.util.Arrays;
import java.util.stream.Stream;

public class StringFormat {

  static Object[] format(String[] input) {
    Stream<String> stream = Arrays.stream(input).filter((str) -> str != null && str.trim().length() != 0).map((str) -> {
      str = str.trim();
      char c = str.charAt(0);
      return String.valueOf(c).toUpperCase() + str.substring(1).toLowerCase();

    });
    return  stream.sorted(String::compareTo).toArray();
  }
}
