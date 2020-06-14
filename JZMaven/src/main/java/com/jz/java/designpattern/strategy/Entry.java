package com.jz.java.designpattern.strategy;

public class Entry {
    public static void main(String[] args) {
        double originalPrice = 700;
        ISale sale =  SaleFactory.getSale("100Off300");
        double finalPrice = sale.calculate(originalPrice);
        System.out.println("Final Price is : " + finalPrice);
    }
}
