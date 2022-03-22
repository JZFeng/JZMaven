package com.jz.java.thread.concurrent.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static final int LOOP = 100000;

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        for(int i = 0; i < 100; i++) {
            System.out.println(counter.getCount());
        }

        System.out.println(counter.getCount());
        System.out.println("WaitNotify End!");

    }
}

class AddThread extends Thread {
    private Counter counter;

    AddThread(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        for (int i = 0; i < ReadWriteLockDemo.LOOP; i++) {
            counter.add(1);
        }
    }
}

class DecThread extends Thread {
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
