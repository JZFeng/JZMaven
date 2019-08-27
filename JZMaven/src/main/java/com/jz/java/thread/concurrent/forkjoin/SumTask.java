package com.jz.java.thread.concurrent.forkjoin;

import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
  private static final int THREASHOLD = 200;
  private int start;
  private int end;
  private long[] array ;

  SumTask(long[] array, int start, int end) {
    this.array = array;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {
    if(end - start <= THREASHOLD) {
      long sum = 0;
      for(int i = start ; i < end; i++  ) {
        sum += array[i];
        try {
          Thread.sleep(2);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return sum;
    }
    int mid = start + (end - start) / 2;
    System.out.println(String.format("split %d~%d ==> %d~%d, %d~%d", start, end, start, mid, mid, end));
    SumTask sumTask1 = new SumTask(array, start, mid);
    SumTask sumTask2 = new SumTask(array, mid, end);
    invokeAll(sumTask1, sumTask2);
    Long r1 = sumTask1.join();
    Long r2 = sumTask2.join();
    return r1 + r2;
  }
}
