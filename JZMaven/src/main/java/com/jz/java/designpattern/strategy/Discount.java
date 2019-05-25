package com.jz.java.designpattern.strategy;

public class Discount implements  ISale{
    private double rate;

    Discount(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculate(double originalPrice) {
        return originalPrice * rate;
    }
}
