/**
 * @Author jzfeng
 * @Date 2/21/23-12:43 PM
 */

package com.jz.multiThread.concurrent.lock;

import java.util.concurrent.atomic.AtomicReference;

class SpinLock {
    private AtomicReference<Thread> cas;

    SpinLock(AtomicReference<Thread> cas){
        this.cas = cas;
    }

    public void lock() {
        Thread current = Thread.currentThread();
        // 利用CAS
        while (!cas.compareAndSet(null, current)) { //为什么预期是null？？
            // DO nothing
            System.out.println("I am spinning");
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        cas.compareAndSet(current, null);
    }
}
