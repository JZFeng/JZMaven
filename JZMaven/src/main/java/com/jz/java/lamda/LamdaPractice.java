package com.jz.java.lamda;

import javax.mail.internet.InternetAddress;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class LamdaPractice {
  public static void main(String[] args) {
    Stream<BigInteger> stream = Stream.generate(new FibSupplier());
    long[] result = stream.limit(20).mapToLong(BigInteger::longValue).toArray();
    for(long l : result) {
      System.out.print(l + " ");
    }


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
