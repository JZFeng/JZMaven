package com.jz.designPattern.proxy.pursuit;

public class Proxy implements IPursuiter {

    RealPursuiter realPursuiter;

    Proxy(RealPursuiter realPursuiter) {
        this.realPursuiter = realPursuiter;
    }


    @Override
    public void sendFlowers() {
        System.out.println("I am working for " + realPursuiter.getName() + ".");
        realPursuiter.sendFlowers();
    }

    @Override
    public void sendChocolates() {
        System.out.println("I am working for " + realPursuiter.getName() + ".");
        realPursuiter.sendChocolates();
    }

    @Override
    public void sendHandbags() {
        System.out.println("I am working for " + realPursuiter.getName() + ".");
        realPursuiter.sendHandbags();
    }
}
