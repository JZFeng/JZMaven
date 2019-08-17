package com.jz.java.thread.basic;

public class HelloThread {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Main Start...");
    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        for(int i = 0 ; i < 3; i++) {
          System.out.println("Anonymous Class as thread");
        }
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    t1.start();
    t1.join();

    Thread t2 = new Thread(new TestThread2("Tom"));
    t2.start();
    t2.join();

    Thread t3 = new Thread(new TestThread2("Bob"));
    t3.start();
    t3.join();

    System.out.println("Main End...");
  }

}

class TestThread implements Runnable {

  private String name;

  TestThread(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    for(int i = 0 ; i < 3; i++) {
      System.out.println("Hello" + name );
    }

    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
