package com.jz.multiThread.basic;

import java.util.concurrent.locks.ReentrantLock;

public class SellTicket {

    public static void main(String[] args) throws InterruptedException {
        SellingTicket sellingTicket = new SellingTicket();
        Thread window1 = new Thread(sellingTicket, "Window1");
        Thread window2 = new Thread(sellingTicket, "Window2");
        Thread window3 = new Thread(sellingTicket, "Window3");
        Thread window4 = new Thread(sellingTicket, "Window4");
        window1.start();
        window2.start();
        window3.start();
        window4.start();
        window1.join();
        window2.join();
        window3.join();
        window4.join();
        System.out.println("WaitNotify thread done.");
    }

}

class SellingTicket implements Runnable {
    private int total_tickets = 400;

    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public void run() {
        reentrantLock.lock();
        try {
            while (true) {
                if (total_tickets <= 0) {
                    System.out.println("Sold Out!");
                    break;
                }
                //Simulating selling a ticket;
                Thread.sleep(1000);
                System.out.println(
                        Thread.currentThread().getName() + " sold a ticket; Remaining: " +
                                total_tickets--);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reentrantLock.unlock();
    }
}
