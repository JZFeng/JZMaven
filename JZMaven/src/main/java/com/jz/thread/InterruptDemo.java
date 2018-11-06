package com.jz.thread;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class InterruptDemo extends Thread {
    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Hello!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
                break;
            }
        }
        System.out.println("Thread end");
    }


    public static void main(String[] args) throws Exception {
        InterruptDemo t = new InterruptDemo();
        t.start();
        Thread.sleep(1000);
        t.interrupt();
        System.out.println("Main end");
    }
}