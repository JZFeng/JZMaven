package com.jz.java.multiThread.sync.deallock;

public class Main {
  public static final int LOOP = 1000;

  public static void main(String[] args) throws InterruptedException {
    ShareObject shareObject = new ShareObject();
    AThread aThread = new AThread(shareObject);
    BThread bThread = new BThread(shareObject);
    aThread.start();
    bThread.start();
    aThread.join();
    bThread.join();
    System.out.println(shareObject.getAmountA() + " ; " + shareObject.getAmountB());
    System.out.println("WaitNotify END!");

  }
}


class AThread extends Thread {
  private ShareObject shareObject;

  AThread(ShareObject shareObject) {
    this.shareObject = shareObject;
  }

  @Override
  public void run() {
    for (int i = 0; i < Main.LOOP; i++) {
      shareObject.atob(1);
    }
  }
}


class BThread extends Thread {
  private ShareObject shareObject;

  BThread(ShareObject shareObject) {
    this.shareObject = shareObject;
  }

  @Override
  public void run() {
    for (int i = 0; i < Main.LOOP; i++) {
      shareObject.btoa(1);
    }
  }
}
