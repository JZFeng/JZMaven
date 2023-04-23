package com.jz.designpattern.chain.linkedListImpl;

//相当于链表节点；
public abstract class Handler {
    public Handler next = null;
    private FeeRequest feeRequest;

    Handler(FeeRequest feeRequest) {
        this.feeRequest = feeRequest;
    }

    //该节点先进行处理；
    public abstract boolean doHandle(FeeRequest feeRequest);

    //模版方法
    public final boolean handle() {
        return this.doHandle(feeRequest);
    }
}
