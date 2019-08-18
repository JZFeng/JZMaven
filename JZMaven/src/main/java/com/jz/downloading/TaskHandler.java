package com.jz.downloading;

import java.io.IOException;

public class TaskHandler extends Thread {
  private TaskQueue taskQueue;

  private boolean isStopped = false;

  TaskHandler(TaskQueue taskQueue) {
    this.taskQueue = taskQueue;
  }

  public void setStopped(boolean flag) {
    this.isStopped = true;
  }

  @Override
  public void run() {
    while(!isStopped) {
      Task task = taskQueue.getTask();
      try {
        Entry.executeATask(task);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
