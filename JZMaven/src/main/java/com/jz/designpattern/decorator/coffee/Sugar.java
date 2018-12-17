package com.jz.designpattern.decorator.coffee;

public class Sugar implements ICoffee {

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
