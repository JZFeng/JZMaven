package com.jz.java.thread.sync.waitnotify;

public class WorkerThread extends Thread {
  private TaskQueue taskQueue;

  WorkerThread(TaskQueue taskQueue) {
    this.taskQueue = taskQueue;
  }

  @Override
  public void run() {
    while (!isInterrupted()) {
      String name ;
      try {
        name = taskQueue.getTask();
      } catch (InterruptedException e) {
        break;
      }
      System.out.println(Thread.currentThread().getName() + ":" + name);
    }
  }
}
