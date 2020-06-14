package com.jz.java.designpattern.decorator.coffee;

public class Sugar extends CoffeeDecorator {


  Sugar(ICoffee coffee) {
    super(coffee);
  }


  @Override
  public String getIngredients() {
    return super.getIngredients() + "Sugar + ";
  }

  @Override
  public double getPrice() {
    return super.getPrice() + 0.5;
  }
}
