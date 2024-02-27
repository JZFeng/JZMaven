package com.jz.multiThread.concurrent.blockingqueue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
    private BlockingQueue<String> bq;
    private Random random;
    private volatile boolean isRunning;
    private static int MAX_SLEEP_TIME = 1000; //全局变量：

    Consumer(BlockingQueue bq){
        this.bq = bq;
        this.isRunning = true;
        this.random = new Random();
    }


    @Override
    public void run() {
        while (isRunning) {
            try {
                String data = bq.poll(5, TimeUnit.SECONDS);
                if(data != null){
                    System.out.println(Thread.currentThread().getName() + "正在消费数据:" + data );
                }
                Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + "消费数据失败");
                throw new RuntimeException(e);
            }
        }
    }

    public void  stop(){
        isRunning = false;
    }
}
