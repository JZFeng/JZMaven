package com.jz.designpattern.chain.linkedListImpl;

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
        Handler cur = head;
        while (cur != null) {
            handled = cur.handle();
            if (handled) {
                break;
            }
            cur = cur.next;
        }

        return handled;
    }
}
