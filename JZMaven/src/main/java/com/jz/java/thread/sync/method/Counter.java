package com.jz.java.thread.sync.method;

//数据封装,把同步逻辑封装到持有数据到实例中去
//Counter class is a thead-safe class
public class Counter {
  private int count = 0;

  public synchronized void add(int n) {
    count += n;
  }

  public synchronized void sub(int n) {
    count -= n;
  }

  public int get() {
    return count;
  }
}
