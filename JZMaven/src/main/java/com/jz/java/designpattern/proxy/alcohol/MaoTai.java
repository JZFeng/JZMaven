package com.jz.java.designpattern.proxy.alcohol;

public class MaoTai implements ISellAlcohol {
    @Override
    public void sellALcohol() {
        System.out.println("I am selling MaoTai");
    }
}