package com.jz.java.designpattern.strategy;

public class Reward implements ISale {
    @Override
    public double calculate(double originalPrice) {
        return originalPrice;
    }
}
