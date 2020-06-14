package com.jz.java.designpattern.chain.arrayImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public class DepartmentManager implements IHandler {
  private FeeRequest feeRequest;

  public DepartmentManager(FeeRequest feeRequest) {
    this.feeRequest = feeRequest;
  }


  @Override
  public boolean doHandle(FeeRequest feeRequest) {
    int amount = feeRequest.getFee();
    if(amount > 500 && amount <= 1000){
      System.out.println("Department manager is approving 500 ~ 1000 fee.");
      return true;
    } else {
      System.out.println("Department manager cannot approve > 1000 fee.");
      return false;
    }
  }

}
