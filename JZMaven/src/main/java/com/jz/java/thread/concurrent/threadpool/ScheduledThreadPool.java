package com.jz.java.thread.concurrent.threadpool;

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {
  public static void main(String[] args)  {
    System.out.println(LocalTime.now());
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    executor.scheduleAtFixedRate(new HelloTask("Tom"),2,5, TimeUnit.SECONDS);
    executor.scheduleWithFixedDelay(new HelloTask("Bob"), 2, 5, TimeUnit.SECONDS);
  }

}

class HelloTask implements Runnable {
  private String name;
  HelloTask(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    System.out.println("Hello " + name + "@" + LocalTime.now());
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Goodbye " + name + "@" + LocalTime.now());
  }
}