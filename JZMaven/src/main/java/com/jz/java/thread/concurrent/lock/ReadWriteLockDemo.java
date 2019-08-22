package com.jz.java.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
  public static final int LOOP = 1000;
  public static void main(String[] args) throws InterruptedException {
    Counter1 counter1 = new Counter1();
    AddThread1 addThread1 = new AddThread1(counter1);
    DecThread1 decThread1 = new DecThread1(counter1);
    addThread1.start();
    decThread1.start();
    addThread1.join();
    decThread1.join();
    System.out.println(counter1.getCount());
    System.out.println("WaitNotify End!");

  }
}

class AddThread1 extends Thread {
  private Counter1 counter1;

  AddThread1(Counter1 counter1) {
    this.counter1 = counter1;
  }

  @Override
  public void run(){
    for(int i = 0 ; i < ReadWriteLockDemo.LOOP; i++) {
      counter1.add(1);
    }
  }
}
class DecThread1 extends Thread {
  private Counter1 counter1;

  DecThread1(Counter1 counter1) {
    this.counter1 = counter1;
  }

  @Override
  public void run(){
    for(int i = 0 ; i < ReadWriteLockDemo.LOOP; i++) {
      counter1.dec(1);
    }
  }
}

class Counter1 {
  private int count = 0;

  private ReadWriteLock lock = new ReentrantReadWriteLock();
  private Lock rlock = lock.readLock();
  private Lock wlock = lock.writeLock();

  public int getCount() {
    rlock.lock();
    try {
      return count;
    } finally {
      rlock.unlock();
    }
  }

  public void add(int n) {
    wlock.lock();
    try{
      count += n;
    }finally {
      wlock.unlock();
    }
  }

  public void dec(int n) {
    wlock.lock();
    try{
      count -= n;
    } finally {
      wlock.unlock();
    }
  }

}
