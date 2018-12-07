package com.jz.designPattern.decorator.coffee;

public class PlainCoffee implements ICoffee {
    @Override
    public String getIngredients() {
        return "Plain Coffee + ";
    }

    @Override
    public double getPrice() {
        return 1;
    }
}
