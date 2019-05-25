package com.jz.java.designpattern.factory.simplefactory;

public class Div implements Operation {

    private double num1;
    private double num2;


    Div(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;
    }
    @Override
    public double getResult() {
        if(num2 == 0) {
            throw new ArithmeticException("/ by zero");
        }

        return num1 / num2;
    }
}
