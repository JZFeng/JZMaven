package com.jz.designpattern.delegator.door;

public class LightDoor extends Door implements Runnable {

    public LightDoor() {
    }

    @Override
    public void open() {
        System.out.println("Light Door Open.");

    }

    @Override
    public void close() {
        System.out.println("Light Door close.");
    }

    @Override
    public void run() {
        boolean isX = false;

        while (true) {
            if (isX) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXX");
                isX = false;
            } else {
                System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZ");
                isX = true;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
