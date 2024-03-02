package com.jz.designpattern.chain;

import java.util.*;

public class HandlerChain {
    private FeeRequest feeRequest;
    private Set<Handler> chain;

    HandlerChain(FeeRequest feeRequest) {
        this.feeRequest = feeRequest;
        this.chain = new TreeSet<>( (a,b) -> a.getLevel() - b.getLevel());
    }

    public void addHandler(Handler handler) {
        chain.add(handler);
    }

    public boolean handle() {
        boolean handled = false;
        for (Handler handler : chain) {
            if (handler.handle()) {
                handled = true;
                break;
            }
        }
        return handled;
    }
}
