package com.jz.designpattern.builder;

public class Entry {
  public static void main(String[] args) throws Exception {
    ResourceConfig resourceConfig = new ResourceConfig.Builder().maxTotal(10).maxIdle(8).minIdle(0).build();
    Pizza pizza = new Pizza.Builder().size(6).hasCheese(false).hasMeat(true).hasPepperoni(false).build();
  }
}
