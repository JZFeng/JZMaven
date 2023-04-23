package com.jz.designpattern.chain.linkedListImpl;

//相当于链表节点；
public abstract class Handler implements IHandler {
    public Handler next = null;
    private FeeRequest feeRequest;

    Handler(FeeRequest feeRequest) {
        this.feeRequest = feeRequest;
    }

    //需要扩展的方法，通过abstract暴露出来;
    public abstract boolean doHandle(FeeRequest feeRequest);

    //模版方法
    public final boolean handle() {
        return this.doHandle(feeRequest);
    }
}
