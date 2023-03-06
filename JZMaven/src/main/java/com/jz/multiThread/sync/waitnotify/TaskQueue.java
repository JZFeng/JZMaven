package com.jz.multiThread.sync.waitnotify;

import java.util.LinkedList;
import java.util.Queue;

//共享数据封装，线程安全的类。synchronized保护的是数据。
public class TaskQueue {
  private final Queue<String> taskQueue = new LinkedList<>();

  public synchronized void addTask(String name) {
    taskQueue.offer(name);
    this.notifyAll();
  }


  public synchronized String getTask() throws InterruptedException {
    while(taskQueue.isEmpty()){
      this.wait();
    }
    return taskQueue.remove();
  }

  public synchronized boolean isEmpty() {
    return taskQueue.isEmpty();
  }

}
