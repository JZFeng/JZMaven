package com.jz.java.thread;

public class InterruptDemo {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Main starts!");
    Thread thread = new TestThread1();
    thread.start();
    Thread.sleep(10);
    thread.interrupt();
    System.out.println("Main ends!");
  }
}


class TestThread1 extends Thread {

  public void run() {
    while (!isInterrupted()) {
      System.out.println("Hello!");
    }
  }
}
