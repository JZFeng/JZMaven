package com.jz.designpattern.strategy;

public class Normal implements ISale {
    @Override
    public double calculate(double originalPrice) {
        return originalPrice;
    }
}
