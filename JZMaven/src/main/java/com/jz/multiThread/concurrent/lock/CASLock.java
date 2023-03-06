/**
 * @Author jzfeng
 * @Date 2/20/23-2:34 PM
 */

package com.jz.multiThread.concurrent.lock;

import java.util.concurrent.atomic.AtomicInteger;

class CASLock {

    AtomicInteger atomicInteger = new AtomicInteger(0);
    Thread currentThread = null;

    public void lock() throws Exception {
        boolean isLock = atomicInteger.compareAndSet(0, 1);
        if (isLock) {
            currentThread = Thread.currentThread();
            System.out.println(currentThread + " tryLock");
        }


    }

    public void unlock() { //1为locked，0为unlocked
        int lockValue = atomicInteger.get();
        if (lockValue == 0) {
            return;
        }
        if (currentThread == Thread.currentThread()) {
            atomicInteger.compareAndSet(1, 0);
            System.out.println(currentThread + " unlock");
        }
    }

    public static void main(String[] args) {
        CASLock casLock = new CASLock();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    casLock.lock();
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    casLock.unlock();
                }
            }).start();
        }

    }
}