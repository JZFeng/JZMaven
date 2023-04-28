package com.jz.multiThread.concurrent.future;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public class CompletableFutureSequence {
    public static void main(String[] args) {
        String name = "上证指数";

        CompletableFuture.supplyAsync(new StockLookup(name))
        .thenApplyAsync(code -> {
            String url = "http://hq.sinajs.cn/list=" + code;
            try {
                Float price = Float.parseFloat(DownloadUtil.download(url).split(",")[3]);
                return new Price(code, price);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        })
        .thenAccept(price -> System.out.println("Stock Code : " + price.code + " ; Current price : " + price.price)).join();

        System.out.println("Entry End");
    }
}

