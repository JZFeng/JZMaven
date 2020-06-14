package com.jz.java.designpattern.chain.arrayImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public class ProjectManager implements IHandler {
  private FeeRequest feeRequest;

  public ProjectManager(FeeRequest feeRequest) {
    this.feeRequest = feeRequest;
  }


  @Override
  public boolean doHandle(FeeRequest feeRequest) {
    if(feeRequest.getFee() <= 500) {
      System.out.println("Project manager is approving <= 500 fee.");
      return true;
    } else {
      System.out.println("Project manager cannot approve >500 fee.");
      return false;
    }
  }
}
