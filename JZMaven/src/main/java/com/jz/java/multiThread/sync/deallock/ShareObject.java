package com.jz.java.multiThread.sync.deallock;

public class ShareObject {
  private  final Object lockA = new Object();
  private  final Object lockB = new Object();

  private int amountA = 1000;
  private int amountB = 2000;

  public void atob(int balance) {
    synchronized (lockA) {
      amountA -= balance;
      synchronized (lockB) {
        amountB += balance;
      }
    }
  }

  public void btoa(int balance) {
    synchronized (lockA) {
      amountA += balance;
      synchronized (lockB) {
        amountB -= balance;
      }
    }
  }

  public int getAmountA() {
    return amountA;
  }

  public int getAmountB() {
    return amountB;
  }
}
