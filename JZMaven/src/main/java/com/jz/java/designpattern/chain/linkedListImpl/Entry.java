package com.jz.java.designpattern.chain.linkedListImpl;

import com.jz.java.designpattern.chain.FeeRequest;

public class Entry {

  public static void main(String[] args) {
    FeeRequest feeRequest = new FeeRequest("Wang", 1550);

    Handler pjctManager = new PjctManager(feeRequest);
    Handler dptManager = new DptManager(feeRequest);
    Handler hr = new HR(feeRequest);

    HandlerChainUsingLinkedList handlers = new HandlerChainUsingLinkedList(feeRequest);
    handlers.addHandler(pjctManager);
    handlers.addHandler(dptManager);
    handlers.addHandler(hr);

    handlers.handle();
//    dfsa
  }



}
