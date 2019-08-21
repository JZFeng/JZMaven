package com.jz.java.thread.concurrent;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class CompletableFutureDemo {
  public static void main(String[] args) {
    //Supplier和Consumer配对使用。
    CompletableFuture cf = CompletableFuture.supplyAsync(new StockpriceSupplier());

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

    for(int i = 0 ; i < 10; i++) {
      System.out.println("*******");
    }

    cf.join();
    System.out.println("Main End");

  }

}

class StockpriceSupplier implements Supplier<Float> {

  @Override
  public Float get() {
    try {
      String result = DownloadUtil.download("http://hq.sinajs.cn/list=sh000001").split(",")[3];
      return Float.parseFloat(result);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


}