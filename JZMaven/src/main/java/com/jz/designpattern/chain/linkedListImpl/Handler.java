package com.jz.designpattern.chain.linkedListImpl;

//抽象类，是所有Handler的父类；
//通过abstract，暴露需要扩展的方法；
//通过模版方法，定义调用；

import lombok.*;

@Getter @Setter  @AllArgsConstructor
public abstract class Handler implements IHandler {
    private FeeRequest feeRequest;
    private String name;
    private int level;

    //需要扩展的方法，通过abstract暴露出来;
    public abstract boolean doHandle(FeeRequest feeRequest);

    //模版方法
    public final boolean handle() {
        return this.doHandle(feeRequest);
    }
}
