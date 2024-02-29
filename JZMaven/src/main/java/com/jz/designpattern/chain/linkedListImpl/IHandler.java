package com.jz.designpattern.chain.linkedListImpl;
//IHandler抽象出来一个接口，代表一个行为，这个行为就是处理报销请求；
public interface IHandler {
   boolean doHandle(FeeRequest feeRequest);
}
