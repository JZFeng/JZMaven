package com.jz.java.designpattern.chain.linkedListImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public class HandlerChainUsingLinkedList {
    private FeeRequest feeRequest;
    private Handler head;
    private Handler tail;

    HandlerChainUsingLinkedList(FeeRequest feeRequest) {
        this.feeRequest = feeRequest;
        head = null;
        tail = null;
    }

    public void addHandler(Handler handler) {
        handler.setNext(null);

        if (head == null) {
            head = handler;
            tail = handler;
            return;
        }

        tail.setNext(handler);
        tail = handler;
    }

    public boolean handle() {
        boolean handled = false;
        Handler current = head;
        while (current != null) {
            handled = current.handle();
            if (handled) {
                break;
            }
        }
        return handled;
    }
}
