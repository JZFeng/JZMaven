/**
 * @Author jzfeng
 * @Date 3/7/22-10:15 PM
 */

package com.jz.multiThread.concurrent.lock;

//[1188. Design Bounded Blocking Queue](https://leetcode.com/problems/design-bounded-blocking-queue/)
//此题可以直接使用ArrayBlockingQueue或者LinkedBlockingQueue，但是面试还是要自己写的；
//BlockingQueue经常使用在生产者消费者模型中；
//可以参照LinkedBlockingQueue或者ArrayBlockingQueue的源码去写；
//源码用的是ReentrantLock和Condition，当然，也可以用信号量Semaphore;


import java.util.LinkedList;
import java.util.concurrent.locks.*;

class BoundedBlockingQueue {
    ReentrantLock lock;
    Condition deque;
    Condition enque;
    LinkedList<Integer> queue;
    Integer size = 0;
    Integer capacity;

    public BoundedBlockingQueue(int capacity) {
        this.lock = new ReentrantLock();
        this.enque = lock.newCondition();
        this.deque = lock.newCondition();
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public void enqueue(int element) throws InterruptedException {
        lock.lock();
        try {
            //阻塞；
            while (size >= capacity) {
                enque.await();
            }
            queue.add(element);
            size++;
            deque.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (size <= 0) {
                deque.await();
            }
            Integer res = queue.removeFirst();
            size--;
            enque.signalAll();
            return res;
        } finally {
            lock.unlock();
        }

    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}



/*
class BoundedBlockingQueue {
    Semaphore enque ;
    Semaphore deque;
    LinkedList<Integer> queue;
    ReentrantLock lock;

    BoundedBlockingQueue(int capacity) {
        this.enque = new Semaphore(capacity);
        //信号量初始化为0，表示
        // all acquire() calls will block and tryAcquire() calls will return false
        // until you do a release()

        this.deque = new Semaphore(0);
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
    }

    public void enqueue(int element) throws InterruptedException {
        enque.acquire();
        queue.add(element);
        deque.release();
    }

    public int dequeue() throws InterruptedException {
        deque.acquire();
        Integer res = queue.removeFirst();
        enque.release();
        return res;
    }

    public int size() {
        lock.lock();
        try{
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}

 */
