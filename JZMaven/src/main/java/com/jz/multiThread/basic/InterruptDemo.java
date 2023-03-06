package com.jz.multiThread.basic;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("WaitNotify starts!");
        TestThread1 thread = new TestThread1();
        thread.start();

        Thread.sleep(5);
        thread.isRunning = false;

        System.out.println("WaitNotify ends!");
    }
}


class TestThread1 extends Thread {
    public volatile boolean isRunning = true;

    public void run() {
        while (isRunning) {
            System.out.println("Hello!");
        }
    }
}
