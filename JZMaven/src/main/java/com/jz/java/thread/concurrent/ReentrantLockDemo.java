package com.jz.java.thread.concurrent;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
  public static final int LOOP = 1000;

  public static void main(String[] args) throws InterruptedException {
    Counter counter = new Counter();
    AddThread addThread = new AddThread(counter);
    DecThread decThread = new DecThread(counter);
    addThread.start();
    decThread.start();
    addThread.join();
    decThread.join();
    System.out.println(counter.getCount());
    System.out.println("WaitNotify End.");
  }

}

class Counter {

  private int count = 0;

  private Lock lock = new ReentrantLock();

  public int getCount() {
    return count;
  }

  public void add(int n) throws InterruptedException {
    lock.tryLock(1, TimeUnit.SECONDS);
    try {
      count += n;
    } finally {
      lock.unlock();
    }
  }

  public void dec(int n) throws InterruptedException {
    lock.tryLock(1, TimeUnit.SECONDS);
    try {
      count -= n;
    } finally {
      lock.unlock();
    }
  }
}

class AddThread extends Thread {
  private Counter counter;

  AddThread(Counter counter) {
    this.counter = counter;
  }

  @Override
  public void run() {
    for (int i = 0; i < ReentrantLockDemo.LOOP; i++) {
      try {
        counter.add(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class DecThread extends Thread {
  private Counter counter;

  DecThread(Counter counter) {
    this.counter = counter;
  }

  @Override
  public void run() {
    for (int i = 0; i < ReentrantLockDemo.LOOP; i++) {
      try {
        counter.dec(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
