package com.jz.designpattern.chain;

public class HR extends Handler {
    HR(FeeRequest feeRequest, String name, int level) {
        super(feeRequest, name, level);
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
