package com.jz.java.designpattern.strategy;

public class Rebate implements ISale {
    private double rebateCondition;
    private double rebate;

    Rebate(double rebateCondition, double rebate) {
        this.rebateCondition = rebateCondition;
        this.rebate = rebate;
    }


    @Override
    public double calculate(double originalPrice) {
        return originalPrice - Math.floor( originalPrice / rebateCondition ) * rebate;
    }
}
