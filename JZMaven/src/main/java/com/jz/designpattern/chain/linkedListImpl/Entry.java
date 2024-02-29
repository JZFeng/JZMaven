package com.jz.designpattern.chain.linkedListImpl;

public class Entry {

    public static void main(String[] args) {
        FeeRequest feeRequest = new FeeRequest("Wang", 850);

        Handler pjctManager = new ProjectManager(feeRequest, "Magic", 1);
        Handler dptManager = new DepartmentManager(feeRequest, "John", 2);
        Handler hr = new HR(feeRequest, "Jefery", 3);

        HandlerChain handlerChain = new HandlerChain(feeRequest);
        handlerChain.addHandler(pjctManager);
        handlerChain.addHandler(dptManager);
        handlerChain.addHandler(hr);

        handlerChain.handle();
    }
}
