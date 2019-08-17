package com.jz.java.thread.sync.waitnotify;

import java.util.Arrays;
import java.util.List;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    TaskQueue taskQueue = new TaskQueue();
    WorkerThread workerThread1 = new WorkerThread(taskQueue);
    WorkerThread workerThread2 = new WorkerThread(taskQueue);
    WorkerThread workerThread3 = new WorkerThread(taskQueue);
    WorkerThread workerThread4 = new WorkerThread(taskQueue);
    workerThread1.start();
    workerThread2.start();
    workerThread3.start();
    workerThread4.start();

    for (int i = 0; i < 10000; i++) {
      taskQueue.addTask("Task" + i);
    }

    Thread.sleep(3000);
    workerThread1.interrupt();
    workerThread2.interrupt();
    workerThread3.interrupt();
    workerThread4.interrupt();
    workerThread1.join();
    workerThread2.join();
    workerThread3.join();
    workerThread4.join();

    System.out.println("Main End!");

  }
}
