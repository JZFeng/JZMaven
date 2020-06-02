package com.jz.java.designpattern.decorator.coffee;

public class Entry {
    public static void main(String[] args) {
        ICoffee coffee = new Tea(new Sugar(new Milk( new Cream(new PlainCoffee()))));
        String ingredients = coffee.getIngredients();
        double price = coffee.getPrice();
        System.out.println(ingredients);
        System.out.println(price);
    }


}
