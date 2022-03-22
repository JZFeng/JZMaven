package com.jz.downloading;

import java.util.concurrent.BlockingQueue;

public class TaskHandler implements Runnable {
    //外部传入，该taskhandler从taskqueue中取任务；
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
        while (!isStopped) { //这个很关键，thread不停；
            Task task = null;
            try {
                task = taskQueue.take(); //从阻塞队列中拿任务；
                if (task != null) {
                    task.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
