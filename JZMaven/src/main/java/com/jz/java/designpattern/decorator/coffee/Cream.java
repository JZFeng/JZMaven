package com.jz.java.designpattern.decorator.coffee;

public class Cream extends CoffeeDecorator {
    ICoffee coffee;

    Cream(ICoffee coffee) {
        super(coffee);
    }


    @Override
    public String getIngredients() {
        return super.getIngredients() + "Cream + ";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 0.5;
    }
}
