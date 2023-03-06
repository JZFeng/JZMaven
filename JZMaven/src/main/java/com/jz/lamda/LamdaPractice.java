package com.jz.lamda;

import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LamdaPractice {
  public static void main(String[] args) {
    Stream<BigInteger> stream = Stream.generate(new FibSupplier());
    stream.limit(20).forEach(System.out::println);


  }

  static class FibSupplier implements Supplier<BigInteger> {
    private BigInteger previous = BigInteger.ONE ;
    private BigInteger current = BigInteger.ONE;


    @Override
    public BigInteger get() {
      BigInteger oldPrevious = previous;
      BigInteger nextValue = previous.add(current);
      previous = current;
      current = nextValue;
      return oldPrevious;
    }
  }
}
