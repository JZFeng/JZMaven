package com.jz.java.designpattern.chain.arrayImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public class Entry {
  public static void main(String[] args) {
    FeeRequest feeRequest = new FeeRequest("Wang", 1550);

    //第一种使用职责链的方法；
    IHandler projectManager = new ProjectManager(feeRequest);
    IHandler depManager = new DepartmentManager(feeRequest);
    IHandler hr = new HumanResource(feeRequest);

    HandlerChainUsingArray chain = new HandlerChainUsingArray(feeRequest);
    chain.addHandler(projectManager);
    chain.addHandler(depManager);
    chain.addHandler(hr);

    chain.handle();
  }
}
