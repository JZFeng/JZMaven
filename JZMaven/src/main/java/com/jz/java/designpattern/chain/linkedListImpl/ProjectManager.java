package com.jz.java.designpattern.chain.linkedListImpl;

public class ProjectManager extends Handler {

    public ProjectManager(FeeRequest feeRequest) {
        super(feeRequest);
    }

    @Override
    public boolean doHandle(FeeRequest feeRequest) {
        int amount = feeRequest.getFee();
        if (amount <= 500 && amount > 0) {
            System.out.println("Project manager is approving <= 500 fee.");
            return true;
        } else {
            System.out.println("Project manager cannot approve >500 fee.");
            return false;
        }
    }
}
