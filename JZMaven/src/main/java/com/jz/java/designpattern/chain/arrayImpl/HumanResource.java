package com.jz.java.designpattern.chain.arrayImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public class HumanResource implements IHandler {
  private FeeRequest feeRequest;

  public HumanResource(FeeRequest feeRequest) {
    this.feeRequest = feeRequest;
  }

  @Override
  public boolean doHandle(FeeRequest feeRequest) {
    int amount = feeRequest.getFee();
    if(amount > 1000) {
      System.out.println("HumanResource is approving >1000 fee.");
      return true;
    } else {
      System.out.println("HumanResource cannot approve < 1000 fee.");
      return false;
    }
  }

}
