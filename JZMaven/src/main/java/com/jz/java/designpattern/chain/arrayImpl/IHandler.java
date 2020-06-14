package com.jz.java.designpattern.chain.arrayImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public interface IHandler {
   boolean doHandle(FeeRequest feeRequest);
}
