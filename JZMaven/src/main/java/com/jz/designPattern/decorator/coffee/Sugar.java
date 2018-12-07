package com.jz.designPattern.decorator.coffee;

public class Sugar extends Decorator {
    ICoffee coffee;

    Sugar(ICoffee coffee) {
        this.coffee = coffee;
    }


    @Override
    public String getIngredients() {
        return coffee.getIngredients() + "Sugar + ";
    }

    @Override
    public double getPrice() {
        return coffee.getPrice() + 0.5;
    }
}
