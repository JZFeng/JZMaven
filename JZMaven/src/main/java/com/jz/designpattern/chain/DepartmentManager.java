package com.jz.designpattern.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class DepartmentManager extends Handler {
    DepartmentManager(FeeRequest feeRequest, String name, int level) {
        super(feeRequest, name, level);
    }

    @Override
    public boolean doHandle(FeeRequest feeRequest) {
        int amount = feeRequest.getFee();
        if (amount > 500 && amount <= 1000) {
            System.out.println("Department manager is approving 500 ~ 1000 fee.");
            return true;
        } else {
            System.out.println("Department manager cannot approve > 1000 fee.");
            return false;
        }
    }
}
