package com.jz.java.designpattern.proxy.cglib;


import com.jz.java.designpattern.proxy.proxy.IProducer;

public class Producer implements IProducer {
    @Override
    public void sellProduct(float money) {
        System.out.println("Sell product and get money : " + money);
    }

    @Override
    public void provideService(float money) {
        System.out.println("Provide service and get money : " + money);
    }
}
