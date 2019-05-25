package com.jz.java.designpattern.factory.abstractfactory;

public class Neg implements Operation {
  private double num1;

  Neg(double num1) {
    this.num1 = num1;
  }

  @Override
  public double getResult() {
    return -num1;
  }
}
