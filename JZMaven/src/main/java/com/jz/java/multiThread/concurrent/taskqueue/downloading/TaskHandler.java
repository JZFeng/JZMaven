package com.jz.java.multiThread.concurrent.taskqueue.downloading;

import java.util.concurrent.BlockingQueue;

//定义成一个Runnable，实际上是充当消费者的角色，不停地执行下载任务；
//任务哪里来呢？从阻塞队列中不停地拿；
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
            try {
                Task task = taskQueue.take(); //从阻塞队列中拿任务；
                if (task != null) {
                    task.execute();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
