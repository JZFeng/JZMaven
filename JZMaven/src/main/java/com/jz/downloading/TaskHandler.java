package com.jz.downloading;

import java.util.concurrent.BlockingQueue;

public class TaskHandler extends Thread {
  private BlockingQueue<Task> taskQueue;

  private boolean isStopped = false;

  TaskHandler(BlockingQueue<Task> taskQueue) {
    this.taskQueue = taskQueue;
  }

  public void setStopFlag(boolean flag) {
    this.isStopped = flag;
  }

  @Override
  public void run() {
    while(!isStopped) {
      Task task = null;
      try {
        task = taskQueue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      try {
//        System.out.println(currentThread().getName() + " downloading " + task.getFolder()  + task.getFilename() );
        Entry.executeATask(task);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
