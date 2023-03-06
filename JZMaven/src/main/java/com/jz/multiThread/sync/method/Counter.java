package com.jz.multiThread.sync.method;

import java.util.concurrent.atomic.AtomicInteger;

//数据封装,把同步逻辑封装到持有数据到实例中去
//Counter class is a thead-safe class
public class Counter {
  private AtomicInteger count = new AtomicInteger(0);

  public void add(int n) {
    count.addAndGet(n);
  }

  public void sub(int n) {
    count.addAndGet(-n);
  }

  public int get() {
    return count.get();
  }
}
