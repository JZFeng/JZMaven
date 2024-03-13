package com.jz.multiThread.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CategoryService {

    public CompletableFuture<List<String>> getCategoriesAsync() {
        return CompletableFuture.supplyAsync( () -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Arrays.asList("Mobile", "Electronics","Network");
        } );
    }
}
