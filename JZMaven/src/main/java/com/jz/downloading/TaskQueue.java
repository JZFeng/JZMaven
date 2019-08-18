package com.jz.downloading;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
  private final Queue<Task> queue = new LinkedList<>();

  public synchronized Task getTask() {
    while(queue.isEmpty()) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        break;
      }
    }
    return queue.remove();
  }

  public synchronized void addTask(Task task) {
    queue.add(task);
  }

  public synchronized boolean isEmpty() {
    return queue.isEmpty();
  }

}
