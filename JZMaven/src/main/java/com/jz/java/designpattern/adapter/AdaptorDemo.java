package com.jz.java.designpattern.adapter;

public class AdaptorDemo {

    public static void main(String[] args) {

        double cost = 2.2d + 0.7d;
        System.out.println("testCost : " + cost);

        Target clientOne = new AdaptorOne();
        clientOne.sampleOperation2();

        Target clientTwo = new AdapterTwo(new Adaptee());
        clientTwo.sampleOperation2();

    }

}
