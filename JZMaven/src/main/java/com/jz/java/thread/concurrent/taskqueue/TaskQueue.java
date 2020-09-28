/**
 * @Author jzfeng
 * @Date 9/27/20-3:46 PM
 */

package com.jz.java.thread.concurrent.taskqueue;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {

    private static final int DEFAULT_MAX_NUM_OF_TASKS = 10;

    private int max_num_of_tasks;

    private Queue<String> queue = new LinkedList<>();

    private Object lock = new Object();

    public TaskQueue(int max_num_of_tasks) {
        this.max_num_of_tasks = max_num_of_tasks;
    }

//

    public TaskQueue() {
        max_num_of_tasks = DEFAULT_MAX_NUM_OF_TASKS;
    }


    public String getTask() {

        try {
            synchronized (lock) {
                while (queue.isEmpty()) {
                    System.out.println("No tasks in the queue, please wait...");
                    lock.wait();
                }

                String task = queue.poll();
                System.out.println("Getting task : " + task + ";current size: " + queue.size());
                lock.notifyAll();
                return task;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return queue.poll();
    }

    public String addTask(String taskName) {

        try {
            synchronized (lock) {
                while (queue.size() >= max_num_of_tasks) {
                    System.out.println("No room for new tasks, please wait...");
                    lock.wait();
                }
                queue.add(taskName);
                System.out.println("Added task: " + queue.peek() + ";current size: " + queue.size());
                lock.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return taskName;
    }
}
