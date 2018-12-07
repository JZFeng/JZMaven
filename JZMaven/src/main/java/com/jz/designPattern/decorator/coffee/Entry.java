package com.jz.designPattern.decorator.coffee;

public class Entry {
    public static void main(String[] args) {
        ICoffee coffee = new Sugar(new Cream(new Sugar(new PlainCoffee())));
        System.out.println(coffee.getIngredients());
        System.out.println(coffee.getPrice());
    }
}
