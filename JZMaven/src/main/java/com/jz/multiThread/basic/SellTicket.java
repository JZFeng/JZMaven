package com.jz.multiThread.basic;

public class SellTicket {

    public static void main(String[] args) throws InterruptedException {
        Ticket ticket = new Ticket();
        Thread window1 = new Thread(ticket, "Window1");
        Thread window2 = new Thread(ticket, "Window2");
        Thread window3 = new Thread(ticket, "Window3");
        Thread window4 = new Thread(ticket, "Window4");
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

class Ticket implements Runnable {
    private int total_tickets = 400;

    private final Object lock = new Object();

    @Override
    public void run() {

        synchronized (lock) {
            while (true) {
                if (total_tickets <= 0) {
                    System.out.println("Sold Out!");
                    break;
                }

                System.out.println(
                        Thread.currentThread().getName() + " sold a ticket; Remaining: " +
                                total_tickets--);
            }
        }

        try {
            //Simulating selling a ticket;
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
