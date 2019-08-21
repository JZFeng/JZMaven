package com.jz.java.thread.sync.variable;

public class SyncDemo {
  public static final int LOOP = 10000;

  public static int count = 0;

  public static final Object lock = new Object();

  public static void main(String[] args) throws InterruptedException {
    Thread t1 = new AddThread();
    Thread t2 = new SubThread();
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println("Count : " + count);
    System.out.println("WaitNotify end");
  }
}

class AddThread extends Thread {

  @Override
  public void run() {
    for (int i = 0; i < SyncDemo.LOOP; i++) {
      synchronized (SyncDemo.lock) {
        SyncDemo.count++;
      }
    }
  }

}

class SubThread extends Thread {
  @Override
  public void run() {
    for (int i = 0; i < SyncDemo.LOOP; i++) {
      synchronized (SyncDemo.lock) {
        SyncDemo.count--;
      }
    }
  }


}
