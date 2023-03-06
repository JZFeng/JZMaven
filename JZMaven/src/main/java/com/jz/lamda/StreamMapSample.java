package com.jz.lamda;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamMapSample {
  public static void main(String[] args){
    String[] strs = "Hello world this is map demo".split(" ");
    Stream<String> stringStream = Arrays.stream(strs);
    stringStream.map(String::toUpperCase).forEach(System.out::println);
  }
}
