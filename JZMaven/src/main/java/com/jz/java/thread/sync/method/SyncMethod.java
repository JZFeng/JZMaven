package com.jz.java.thread.sync.method;

import com.jz.java.designpattern.factory.factorymethod.Add;

public class SyncMethod {

  public static final int LOOP = 10000;

  public static void main(String[] args) throws InterruptedException {
    Counter counter = new Counter();
    Thread thread1 = new AddThread(counter);
    Thread thread2 = new SubThread(counter);
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();
    System.out.println(counter.get());
  }


}

class AddThread extends Thread {
  private Counter counter;

  AddThread(Counter counter) {
    this.counter = counter;
  }

  @Override
  public void run() {
    for (int i = 0; i < SyncMethod.LOOP; i++) {
      counter.add(1);
    }
  }
}


class SubThread extends Thread {
  private Counter counter;

  SubThread(Counter counter) {
    this.counter = counter;
  }

  @Override
  public void run() {
    for (int i = 0; i < SyncMethod.LOOP; i++) {
      counter.sub(1);
    }

  }
}

