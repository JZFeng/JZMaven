package com.jz.multiThread.sync.waitnotify;

public class WaitNotify {

  public static void main(String[] args) throws InterruptedException {
    TaskQueue taskQueue = new TaskQueue();
    WorkerThread workerThread1 = new WorkerThread(taskQueue);
    workerThread1.setName("Worker 1");
    WorkerThread workerThread2 = new WorkerThread(taskQueue);
    workerThread2.setName("Worker 2");
    WorkerThread workerThread3 = new WorkerThread(taskQueue);
    workerThread3.setName("Worker 4");
    WorkerThread workerThread4 = new WorkerThread(taskQueue);
    workerThread4.setName("Worker 4");
    workerThread4.setDaemon(true);
    workerThread1.start();
    workerThread2.start();
    workerThread3.start();
    workerThread4.start();

    for (int i = 0; i < 10000; i++) {
      taskQueue.addTask("Task" + i);
    }

    Thread.sleep(2000);
    workerThread1.interrupt();
    workerThread2.interrupt();
    workerThread3.interrupt();
    workerThread4.interrupt();
    workerThread1.join();
    workerThread2.join();
    workerThread3.join();
    workerThread4.join();
    System.out.println("WaitNotify End!");

  }
}
