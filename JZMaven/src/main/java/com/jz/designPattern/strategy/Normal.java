package com.jz.designPattern.strategy;

public class Normal implements ISale {
    @Override
    public double calculate(double originalPrice) {
        return originalPrice;
    }
}
