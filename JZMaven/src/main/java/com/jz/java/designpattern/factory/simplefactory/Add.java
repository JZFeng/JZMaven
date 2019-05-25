package com.jz.java.designpattern.factory.simplefactory;

public class Add implements Operation{
    private double num1;
    private double num2;

    Add(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;
    }

    @Override
    public double getResult() {
        return num1 + num2;
    }
}
