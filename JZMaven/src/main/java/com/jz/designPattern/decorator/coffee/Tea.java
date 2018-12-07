package com.jz.designPattern.decorator.coffee;

public class Tea implements ICoffee {
    ICoffee coffee;

    Tea(ICoffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getIngredients() {
        return coffee.getIngredients() + "Tea + ";
    }

    @Override
    public double getPrice() {
        return coffee.getPrice() + 1;
    }
}
