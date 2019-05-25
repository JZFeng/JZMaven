package com.jz.java.designpattern.factory.factorymethod;

public class Entry {
  public static void main(String[] args) {
    double num1 = 1.23;
    double num2 = 4.56;

    Operation operation = new AddFactory().createOperation(num1, num2);
    System.out.println(operation.getResult());

    operation = new SubFactory().createOperation(num1, num2);
    System.out.println(operation.getResult());
  }
}
