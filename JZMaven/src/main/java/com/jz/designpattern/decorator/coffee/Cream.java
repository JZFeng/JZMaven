package com.jz.designpattern.decorator.coffee;

public class Cream extends CoffeeDecorator {

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
