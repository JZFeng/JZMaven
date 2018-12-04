package com.jz.designPattern.strategy;

public class Entry {
    public static void main(String[] args) {
        double originalPrice = 700;
        Context context = new Context("100Off300");
        double finalPrice = context.calculate(originalPrice);
        System.out.println("Final Price is : " + finalPrice);
    }
}
