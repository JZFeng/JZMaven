package com.jz.java.designpattern.chain.linkedListImpl;

public class HR extends Handler {
    HR(FeeRequest feeRequest) {
        super(feeRequest);
    }

    @Override
    public boolean doHandle(FeeRequest feeRequest) {
        int amount = feeRequest.getFee();
        if (amount > 1000) {
            System.out.println("HumanResource is approving >1000 fee.");
            return true;
        } else {
            System.out.println("HumanResource cannot approve < 1000 fee.");
            return false;
        }
    }


}
