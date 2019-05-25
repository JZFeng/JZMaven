package com.jz.java.designpattern.proxy.pursuit;

public class RealPursuiter implements IPursuiter {
    private String name;

    RealPursuiter(String name) {
        this.name = name;
    }


    @Override
    public void sendFlowers() {
        System.out.println("Sending flowers.");
    }

    @Override
    public void sendChocolates() {
        System.out.println("Sending chocolates.");
    }

    @Override
    public void sendHandbags() {
        System.out.println("Sending handbags.");
    }

    public String getName() {
        return name;
    }
}
