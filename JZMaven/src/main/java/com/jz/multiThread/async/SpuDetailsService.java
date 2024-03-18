package com.jz.multiThread.async;

import java.util.concurrent.CompletableFuture;

public class SpuDetailsService {
    CompletableFuture<String> getSpuDetailsByID(Long spuid){
         return CompletableFuture.supplyAsync( () -> {return "1000";} );
    }
}
