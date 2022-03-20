package com.jz.java.designpattern.adapter;

public class AdapterTwo implements Target {

    private Adaptee adaptee;

    public AdapterTwo(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void sampleOperation1() {
        // TODO Auto-generated method stub
        this.adaptee.sampleOperation1();

    }

    @Override
    public void sampleOperation2() {
        // TODO Auto-generated method stub
        System.out.println("Sample Operation 2");

    }

}
