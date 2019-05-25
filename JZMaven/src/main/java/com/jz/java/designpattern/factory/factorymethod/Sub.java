package com.jz.java.designpattern.factory.factorymethod;

public class Sub implements Operation {

  double num1;
  double num2;

  Sub(double num1, double num2) {
    this.num1 = num1;
    this.num2 = num2;
  }

  @Override
  public double getResult() {
    return num1 - num2;
  }
}
