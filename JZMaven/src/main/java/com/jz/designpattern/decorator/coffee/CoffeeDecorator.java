package com.jz.designpattern.decorator.coffee;

//抽象出这一个类，是避免子类中代码重复的问题。子类中不需要改变的，缺省使用父类的实现即可。
//如果没有此父类，则子类中所有的方法都必须重写；
public abstract class CoffeeDecorator implements ICoffee {
  ICoffee coffee;

  CoffeeDecorator(ICoffee coffee) {
    this.coffee = coffee;
  }


  @Override
  public String getIngredients() {
    return coffee.getIngredients();
  }

  @Override
  public double getPrice() {
    return coffee.getPrice();
  }
}
