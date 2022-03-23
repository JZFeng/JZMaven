package com.jz.java.multiThread.basic;

import java.time.LocalTime;

public class DeamonDemo {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("WaitNotify start:");
    Thread thread = new DeamonThread();
    thread.setDaemon(true);
    thread.start();
    Thread.sleep(5000);

    System.out.println("WaitNotify end:");
  }
}

class DeamonThread extends Thread {

  @Override
  public void run() {
    while (true) {
      System.out.println(LocalTime.now());
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
  }
}
