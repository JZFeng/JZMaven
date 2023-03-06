package com.jz.lamda;

import java.util.Arrays;

public class ReduceSample2 {
  public static void main(String[] args) {
    String[] strs =  "Stream API supports functional-style operations".split(" ");
    String s = Arrays.stream(strs).map(String::toLowerCase).reduce((acc, str) -> acc + " ~ " + str).get();
    System.out.println(s);
  }
}
