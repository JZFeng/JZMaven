package com.jz.thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelloThread extends Thread {
    public String name;

    HelloThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        System.out.println("Hello Start " + name);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Hello End " + name);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("START");
        List<Thread> threads = new ArrayList<>();
        for(String name : Arrays.asList("Tom", "Jack", "Alice")) {
            threads.add(new HelloThread(name));
        }

        for(Thread thread : threads) {
            thread.start();
        }

        for(Thread thread : threads) {
            thread.join();
        }

        System.out.println("END");


    }
}
