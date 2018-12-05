package com.jz.designPattern.simpleFactory;

public class Neg implements Operation {

    double num1;

    Neg(double num1) {
        this.num1 = num1;
    }

    @Override
    public double getResult() {
        return -num1;
    }
}
