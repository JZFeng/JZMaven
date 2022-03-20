package com.jz.java.designpattern.adapter;

public class AdaptorOne extends Adaptee implements Target {

    public AdaptorOne() {

    }

    @Override
    public void sampleOperation1() {
        super.sampleOperation1();

    }

    @Override
    public void sampleOperation2() {
        System.out.println("Sample Operation 2");

    }

}
