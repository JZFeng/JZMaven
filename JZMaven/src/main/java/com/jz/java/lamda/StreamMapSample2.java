package com.jz.java.lamda;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamMapSample2 {
  public static void main(String[] args) {
    String[] inputs = { "Bob,M", "Alice,F", "Time,M", "Lily,F" };
    Stream<String> names = Arrays.stream(inputs);
    Stream<Person> personStream = names.map((s) -> {
      int index = s.indexOf(',');
      String n = s.substring(0,index);
      char g = s.charAt(index + 1);
      return new Person(n, g);
    });
    personStream.forEach(System.out::println);
  }
}

class Person{
  private String name;
  private char gender;

  Person(String name, char gender) {
    this.name = name;
    this.gender = gender;
  }

  @Override
  public String toString() {
    return "Person(" + name + "," + gender + ")";
  }
}
