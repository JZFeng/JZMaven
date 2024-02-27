package com.jz.multiThread.concurrent.blockingqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{

    private BlockingQueue<String> bq = new ArrayBlockingQueue<>(10); //所有生产者的共享数据，由外部传入
    private AtomicInteger count; //全局变量：记录所有生产者的总生产量,所有生产者的共享数据;由外部传入；

    private volatile boolean isRunning; //局部变量，控制当前生产者
    private Random random; //局部变量

    private static int MAX_SLEEP_TIME = 1000; //全局变量：

    Producer(BlockingQueue<String> bq, AtomicInteger count) {
        this.bq = bq;
        this.count = count;
        this.isRunning = true;
        this.random = new Random();
    }


    @Override  //生产者的具体操作代码；
    public void run() {
        while (isRunning) {
            int sleepTime = random.nextInt(MAX_SLEEP_TIME);
            try {
                Thread.sleep(sleepTime);//模拟生产过程
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                int nth = count.incrementAndGet();
                if(bq.offer( "数据编号:" + nth, 2, TimeUnit.SECONDS)){
                    System.out.println(Thread.currentThread().getName() + "生产数据成功：" + nth);
                }
            } catch (Exception e) {
                System.out.println("生产数据失败");
                break;
            }
        }
    }

    public void stop(){
        this.isRunning = false;
    }
}
