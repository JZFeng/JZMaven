package com.jz.java.designpattern.factory.abstractfactory;

public class Div implements Operation {

  private double num1;
  private double num2;

  Div(double num1, double num2) {
    this.num1 = num1;
    this.num2 = num2;
  }


  @Override
  public double getResult() {
    if(Math.abs(num2 - 0) < 0.0000000001) {
      throw new ArithmeticException("Divided by zero.");
    }

    return num1 / num2;
  }
}
