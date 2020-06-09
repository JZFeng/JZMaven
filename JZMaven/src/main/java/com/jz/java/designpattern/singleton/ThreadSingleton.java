package com.jz.java.designpattern.singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadSingleton {

  private AtomicLong atomicLong = new AtomicLong(0);

  private static final ConcurrentHashMap<Long, ThreadSingleton> instances = new ConcurrentHashMap<>();

  private ThreadSingleton(){
  }

  private  long getId(){
    return atomicLong.incrementAndGet();
  }

  public static ThreadSingleton getInstance() {
    long currentThreadId = Thread.currentThread().getId();

    if(instances.get(currentThreadId) == null) {
      synchronized (ThreadSingleton.class){
          instances.putIfAbsent(currentThreadId, new ThreadSingleton());
      }
    }

    return instances.get(currentThreadId);
  }

  public static void main(String[] args) {
    System.out.println(ThreadSingleton.getInstance().getId());
  }

}
