package com.jz.designpattern.decorator.coffee;

public class Milk extends CoffeeDecorator {
  Milk(ICoffee coffee) {
    super(coffee);
  }

  @Override
  public String getIngredients() {
    return coffee.getIngredients() + "Milk + ";
  }


}
