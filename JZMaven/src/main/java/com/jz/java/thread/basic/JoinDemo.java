package com.jz.java.thread.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinDemo {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("WaitNotify starts...");
    List<Thread> threads = new ArrayList<>();
    for(String name : Arrays.asList("Alice", "Bob", "Tom")) {
      threads.add(new Thread(new TestThread2(name)));
    }

    for(Thread thread : threads) {
      thread.start();
    }

    for(Thread thread : threads) {
      thread.join();
    }

    System.out.println("WaitNotify ends...");
  }
}

class TestThread2 implements Runnable {
  private String name ;

  TestThread2(String name) {
    this.name = name;
  }


  @Override
  public void run() {
    System.out.println("Hello " + name + "!");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Goodbye " + name + "!");
  }
}
