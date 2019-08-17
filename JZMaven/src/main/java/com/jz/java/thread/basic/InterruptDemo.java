package com.jz.java.thread.basic;

public class InterruptDemo {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Main starts!");
    TestThread1 thread = new TestThread1();
    thread.start();

    Thread.sleep(5);
    thread.isRunning = false;

    System.out.println("Main ends!");
  }
}


class TestThread1 extends Thread {
  public volatile boolean isRunning = true;

  public void run() {
    while (isRunning) {
      System.out.println("Hello!");
    }
  }
}
