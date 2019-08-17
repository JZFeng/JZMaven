package com.jz.java.thread.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter{

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