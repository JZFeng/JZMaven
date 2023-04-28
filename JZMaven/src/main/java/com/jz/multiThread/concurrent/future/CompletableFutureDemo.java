package com.jz.multiThread.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        //Supplier和Consumer配对使用。
        CompletableFuture cf = CompletableFuture.supplyAsync(new StockpriceSina())
                .thenAccept( s -> System.out.println("Current stock price : " + s));

        cf.exceptionally( e -> {
            System.out.println("Error is " + e.toString());
            return Float.NaN;
        });

        for (int i = 0; i < 10; i++) {
            System.out.println("*******");
        }

        cf.join();
        System.out.println("Entry End");
    }
}

