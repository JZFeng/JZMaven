package com.jz.designpattern.chain;

public class ProjectManager extends Handler {

    ProjectManager(FeeRequest feeRequest, String name, int level) {
        super(feeRequest, name, level);
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
