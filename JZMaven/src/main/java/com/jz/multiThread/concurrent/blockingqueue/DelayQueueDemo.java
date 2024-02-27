package com.jz.multiThread.concurrent.blockingqueue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo {
    public static void main(String[] args) throws ParseException {
        DelayQueue<DelayedTask> dq = new DelayQueue<>();
        dq.offer(new DelayedTask("task in 1s", 1));
        dq.offer( new DelayedTask("task in 3s",3));
        dq.offer(new DelayedTask("task in 5s", 5));
        dq.offer(new DelayedTask("task in at a future time", "2024-02-27 12:28:00"));
        while (!dq.isEmpty()){
            DelayedTask task = dq.poll();
            if(task != null){
                System.out.println(task.taskName);
            }
        }
    }
}

class DelayedTask implements Delayed {
    String taskName;
    long executionTimeStamp;

    //传入的数据有很多种类，如n秒，如将来某个时间，不过都可以转化为一个timestamp;

    // "2024-02-29 09:00:00"
    DelayedTask(String taskName, String dateString) throws ParseException {
        this.taskName = taskName;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
        this.executionTimeStamp = date.getTime();
    }

    DelayedTask(String taskName, int delayTimeInSeconds ) {
        this.taskName=  taskName;
        this.executionTimeStamp = 1000 * delayTimeInSeconds + System.currentTimeMillis();
    }

    @Override //还有多久出队列;
    public long getDelay(TimeUnit unit) {
        return executionTimeStamp - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask task = (DelayedTask) o;
        return (int) (this.executionTimeStamp - task.executionTimeStamp);
    }
}