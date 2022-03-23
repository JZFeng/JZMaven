package com.jz.java.multiThread.concurrent.threadpool;

import java.util.concurrent.*;

public class ThreadPool {

  public static void main(String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(3);


    Runnable r1 = new PrintTask("Bob");
    Runnable r2 = new PrintTask("Tom");
    Runnable r3 = new PrintTask("Alice");
    Runnable r4 = new PrintTask("Jack");
    executor.submit(r1);
    executor.submit(r2);
    executor.submit(r3);
    executor.submit(r4);
    Thread.sleep(10000);
    executor.shutdown();
  }

}


class PrintTask implements Runnable {
  private String name;

  PrintTask(String name ) {
    this.name = name;
  }

  @Override
  public void run() {

    for(int i = 0 ; i < 3; i++) {
      System.out.println("Hello " + name);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}