package com.jz.multiThread.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockingQueueDemo {
    private static AtomicInteger total_count = new AtomicInteger();

    public static void main(String[] args) throws Exception {

        BlockingQueue bq = new ArrayBlockingQueue(20);
        Producer producer = new Producer(bq, total_count);
        Consumer consumer = new Consumer(bq);

        Thread consumer1 = new Thread(consumer, "消费者1");
        Thread consumer2 = new Thread(consumer, "消费者2");
        Thread producer1 = new Thread(producer, "生产者1");
        Thread producer2 = new Thread(producer, "生产者2");
        Thread producer3 = new Thread(producer, "生产者3");
        producer1.start();
        producer2.start();
        producer3.start();
        consumer1.start();
        consumer2.start();

        Thread.sleep(10000);
        producer1.stop();
        producer2.stop();
        producer3.stop();
    }
}
