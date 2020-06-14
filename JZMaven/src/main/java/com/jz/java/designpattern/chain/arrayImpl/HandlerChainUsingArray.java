package com.jz.java.designpattern.chain.arrayImpl;

import com.jz.java.designpattern.chain.FeeRequest;

import java.util.ArrayList;
import java.util.List;

public class HandlerChainUsingArray {

  private FeeRequest feeRequest;
  private List<IHandler> IHandlers = new ArrayList<>();

  public HandlerChainUsingArray(FeeRequest feeRequest) {
    this.feeRequest = feeRequest;
  }

  public void addHandler(IHandler IHandler) {
    this.IHandlers.add(IHandler);
  }

  public boolean handle(){
    for(IHandler IHandler : IHandlers) {
      if(IHandler.doHandle(feeRequest)){
        return true;
      }
    }

    return false;
  }
}
