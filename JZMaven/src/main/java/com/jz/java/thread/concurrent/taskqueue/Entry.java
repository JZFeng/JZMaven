/**
 * @Author jzfeng
 * @Date 9/27/20-4:03 PM
 */

package com.jz.java.thread.concurrent.taskqueue;

public class Entry {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue(10);

        new Thread(new Runnable() {

            private int i = 0;
            @Override
            public void run() {

                try {

                    while (true) {
                        Thread.sleep(1000);
                        taskQueue.addTask("Task" + i++ );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    while (true) {
                        Thread.sleep(2000);
                        taskQueue.getTask();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}
