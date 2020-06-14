package com.jz.java.designpattern.decorator.coffee;

public class Tea extends CoffeeDecorator {

    Tea(ICoffee coffee) {
        super(coffee);
    }

    @Override
    public String getIngredients() {
        return super.getIngredients() + "Tea + ";
    }

    @Override
    public double getPrice() {
        return super.getPrice() + 1;
    }
}
