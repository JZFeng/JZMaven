package com.jz.java.designpattern.chain.linkedListImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public abstract class Handler {
  private Handler successor = null;
  private FeeRequest feeRequest;

  Handler(FeeRequest feeRequest) {
    this.feeRequest = feeRequest;
  }

  public void setSuccessor(Handler successor) {
    this.successor = successor;
  }

  public abstract boolean doHandle(FeeRequest feeRequest) ;

  //模版方法
  public final boolean handle(){
    boolean handled = doHandle(feeRequest);
    if(!handled && successor != null) {
      handled = successor.handle();
    }

    return handled;
  }

}
