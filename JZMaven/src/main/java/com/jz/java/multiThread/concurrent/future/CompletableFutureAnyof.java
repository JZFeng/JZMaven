package com.jz.java.multiThread.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CompletableFutureAnyof {
  public static void main(String[] args) {
    CompletableFuture<StockPrice> cfsina = CompletableFuture.supplyAsync(new StockpriceSina());
    CompletableFuture<StockPrice> cfsohu = CompletableFuture.supplyAsync(new StockpriceSohu());
    CompletableFuture<Object> cf_final = CompletableFuture.anyOf(cfsina, cfsohu);

    cf_final.thenAccept(new Consumer<Object>() {
      @Override
      public void accept(Object o) {
        System.out.println("Stock price : " + o);
      }

    });

    cf_final.join();
  }
}


