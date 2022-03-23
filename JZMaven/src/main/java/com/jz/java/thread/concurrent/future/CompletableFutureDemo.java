package com.jz.java.thread.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        //Supplier和Consumer配对使用。
        CompletableFuture cf = CompletableFuture.supplyAsync(new StockpriceSina());

        cf.thenAccept(new Consumer<Float>() {
            @Override
            public void accept(Float s) {
                System.out.println("Current stock price : " + s);
            }
        });


        cf.exceptionally(new Function<Throwable, Float>() {
            @Override
            public Float apply(Throwable throwable) {
                System.out.println("Error is " + throwable.getMessage());
                return Float.NaN;
            }
        });

        for (int i = 0; i < 10; i++) {
            System.out.println("*******");
        }

        cf.join();
        System.out.println("Entry End");

    }

}

