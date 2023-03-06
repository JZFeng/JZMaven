package com.jz.multiThread.concurrent.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoin {


  public static void main(String[] args) {
    Random random = new Random(0);
    long[] array = new long[1000];
    long expectedResult = 0;
    for(int i = 0 ; i < array.length; i++) {
      array[i] = random.nextInt(10000);
      expectedResult += array[i];
    }

    System.out.println("Expected Sum : " + expectedResult);

    long startTime = System.currentTimeMillis();
    ForkJoinTask<Long> task = new SumTask(array, 0 , array.length);
    Long result = ForkJoinPool.commonPool().invoke(task);
    long endTime = System.currentTimeMillis();
    System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");

  }
}
