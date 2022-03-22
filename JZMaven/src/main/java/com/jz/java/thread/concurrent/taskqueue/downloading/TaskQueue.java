package com.jz.java.thread.concurrent.taskqueue.downloading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Deprecated
public class TaskQueue {
  private final Queue<Task> queue = new LinkedList<>();

  private final ReentrantLock lock = new ReentrantLock();

  private final Condition condition = lock.newCondition();

  public Task getTask() throws InterruptedException {
    lock.lock();
    try {
      while (queue.isEmpty()) {
        condition.await();
      }
      return queue.remove();
    } finally {
      lock.unlock();
    }

  }

  public void addTask(Task task) {
    lock.lock();
    try{
      queue.add(task);
      condition.signalAll();
    } finally {
      lock.unlock();
    }
  }

  public boolean isEmpty() {
    lock.lock();
    try{
      return queue.isEmpty();
    } finally {
      lock.unlock();
    }
  }

}
