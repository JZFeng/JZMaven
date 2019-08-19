package com.jz.downloading;

public class TaskHandler extends Thread {
  private TaskQueue taskQueue;

  private boolean isStopped = false;

  TaskHandler(TaskQueue taskQueue) {
    this.taskQueue = taskQueue;
  }

  public void setStopFlag(boolean flag) {
    this.isStopped = flag;
  }

  @Override
  public void run() {
    while(!isStopped) {
      Task task = taskQueue.getTask();
      try {
        System.out.println(currentThread().getName() + " downloading " + task.getFolder() +"/" + task.getFilename() + ".mp4");
        Entry.executeATask(task);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
