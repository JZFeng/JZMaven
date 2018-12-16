package com.jz.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericSuper {

  public static void main(String[] args) throws Exception {
    Pair<Integer> p = new Pair<>(0,0);
    set(p, 123,456);
    System.out.println(p);

    Pair<Number> pair = new Pair<>(123.4,234.5);
    set(pair, 99,88);
    System.out.println(pair);

    List<String> source = Arrays.asList("I", "Love", "Jason");
    List<String> dest = new ArrayList<>();

    GenericHelper.copy(dest, source);
    System.out.println(dest);

    List<Double> src = Arrays.asList(1.2,2.3,3.4,4.5,5.6);
    List<Number> dst = new ArrayList<>();
    GenericHelper.copy(dst, src);
    System.out.println(dst);


  }

  public static void set(Pair<? super Integer> pair, Integer first, Integer last) {
    pair.setFirst(first);
    pair.setLast(last);
  }



}
