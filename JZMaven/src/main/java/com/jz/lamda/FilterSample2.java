package com.jz.lamda;

import java.util.Arrays;

public class FilterSample2 {
  public static void main(String[] args) {
    String[] array = { "Java", " Python ", " ", null, "\n\n", " Ruby " };
    Arrays.stream(array).filter((s) -> s != null && s.trim().length() != 0).map(String::trim).forEach(System.out::println);
  }
}
