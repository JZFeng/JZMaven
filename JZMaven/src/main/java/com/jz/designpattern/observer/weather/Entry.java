package com.jz.designpattern.observer.weather;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Entry {
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Clock clock = new Clock();
        Board board = new Board();
        clock.addClockSubscriber(board);

        //整点报时；
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Date date = new Date();
                    if(date.getMinutes() == 0 ) {
                        clock.update(date.getHours());
                    }
                }
            }
        });
    }
}
