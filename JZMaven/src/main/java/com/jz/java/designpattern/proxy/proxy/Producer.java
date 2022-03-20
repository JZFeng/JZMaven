package com.jz.java.designpattern.proxy.proxy;


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
