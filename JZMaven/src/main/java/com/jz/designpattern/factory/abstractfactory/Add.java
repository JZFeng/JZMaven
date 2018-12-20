package com.jz.designpattern.factory.abstractfactory;

public class Add implements Operation {
  private double num1;
  private double num2;

  public Add(double num1, double num2) {
    this.num1 = num1;
    this.num2 = num2;
  }

  public double getResult() {
    return num1 + num2;
  }
}
