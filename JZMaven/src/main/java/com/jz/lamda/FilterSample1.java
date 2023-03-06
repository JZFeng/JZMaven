package com.jz.lamda;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class FilterSample1 {
  public static void main(String[] args) {
    Stream<Long> stream =  Stream.generate(new NaturalSupplier());
    stream.filter((n) -> n % 5 == 0).limit(20).forEach(System.out::println);
  }

}

class NaturalSupplier implements Supplier<Long> {
  private long start = 0;

  @Override
  public Long get() {
    return start++;
  }
}