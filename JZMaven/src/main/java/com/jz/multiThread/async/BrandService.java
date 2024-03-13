package com.jz.multiThread.async;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BrandService {
    public CompletableFuture<List<String>> getBrandsAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return Arrays.asList("Huawei","Mi","OnePlus");
        });
    }
}
