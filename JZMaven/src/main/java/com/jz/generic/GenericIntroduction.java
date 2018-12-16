package com.jz.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenericIntroduction {
  public static void main(String[] args) {
    List<String> strs = new ArrayList<>();
    strs.addAll(Arrays.asList("Xiao Ming", "Jason Feng", "Maggie Du"));
    System.out.println(strs);
    Collections.sort(strs);
    System.out.println(strs);

    List<Student> students = Arrays.asList(new Student("JZ", 230), new Student("Jason", 285), new Student("Evelyn", 230));
    System.out.println(students);
    Collections.sort(students);
    System.out.println(students);

    Pair<String> p = new Pair<>("JZ", "95131");
    System.out.println(p);

    Pair<Integer> pair = Pair.create(1,2);
    System.out.println(pair);
    System.out.println(pair.getClass() == Pair.class);
  }
}
