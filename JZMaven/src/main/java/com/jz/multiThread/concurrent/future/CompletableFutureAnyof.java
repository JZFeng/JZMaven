package com.jz.multiThread.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class CompletableFutureAnyof {
  public static void main(String[] args) throws Exception {
    CompletableFuture<StockPrice> cfsina = CompletableFuture.supplyAsync(new StockpriceSina());
    CompletableFuture<StockPrice> cfsohu = CompletableFuture.supplyAsync(new StockpriceSohu());
    CompletableFuture<Object> cf_final = CompletableFuture.anyOf(cfsina, cfsohu);
    cf_final.join();
  }
}


