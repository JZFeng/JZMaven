package com.jz.java.designpattern.chain.linkedListImpl;

import com.jz.java.designpattern.chain.FeeRequest;

//相当于链表节点；
public abstract class Handler {
    private Handler next = null;
    private FeeRequest feeRequest;

    Handler(FeeRequest feeRequest) {
        this.feeRequest = feeRequest;
    }

    public void setNext(Handler next) {
        this.next = next;
    }

    //该节点先进行处理；
    public abstract boolean doHandle(FeeRequest feeRequest);

    //模版方法
    public final boolean handle() {
        boolean handled = doHandle(feeRequest);

        if (!handled && next != null) {
            handled = next.handle();
        }

        return handled;
    }

}
