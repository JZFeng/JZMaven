package com.jz.java.designpattern.decorator.coffee;

public class Cream implements ICoffee {
    ICoffee coffee;

    Cream(ICoffee coffee) {
        this.coffee = coffee;
    }


    @Override
    public String getIngredients() {
        return coffee.getIngredients() + "Cream + ";
    }

    @Override
    public double getPrice() {
        return coffee.getPrice() + 0.5;
    }
}
