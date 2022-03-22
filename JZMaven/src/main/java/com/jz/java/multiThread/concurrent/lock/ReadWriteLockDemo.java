package com.jz.java.multiThread.concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static final int LOOP = 100000;
    public static final int NUM_OF_INC_THREADS = 3;
    public static final int NUM_OF_DEC_THREADS = 2;

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_INC_THREADS + NUM_OF_DEC_THREADS);
        for (int i = 0; i < NUM_OF_INC_THREADS; i++) {
            executorService.submit(new IncThread(counter));
        }
        for (int i = 0; i < NUM_OF_DEC_THREADS; i++) {
            executorService.submit(new DecThread(counter));
        }
        for (int i = 0; i < 10; i++) {
            Thread.sleep(2);
            System.out.println(counter.getCount());
        }
        executorService.shutdown();
    }

}

class IncThread implements Runnable {
    private Counter counter;

    IncThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < ReadWriteLockDemo.LOOP; i++) {
            counter.add(1);
        }
    }
}

class DecThread implements Runnable {
    private Counter counter;

    DecThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < ReadWriteLockDemo.LOOP; i++) {
            counter.dec(1);
        }
    }
}

class Counter {
    private int count = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock rlock = lock.readLock();
    private Lock wlock = lock.writeLock();

    public int getCount() {
        rlock.lock();
        try {
            return count;
        } finally {
            rlock.unlock();
        }
    }

    public void add(int n) {
        wlock.lock();
        try {
            count += n;
        } finally {
            wlock.unlock();
        }
    }

    public void dec(int n) {
        wlock.lock();
        try {
            count -= n;
        } finally {
            wlock.unlock();
        }
    }

}
