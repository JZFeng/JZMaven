package com.jz.lamda;

import java.util.stream.Stream;

public class ReduceSample1 {
  public static void main(String[] args) {
    int r = Stream.of(1,2,3,4,5,6).reduce(1000,(acct, n) -> acct + n);
    System.out.println(r);

  }
}
