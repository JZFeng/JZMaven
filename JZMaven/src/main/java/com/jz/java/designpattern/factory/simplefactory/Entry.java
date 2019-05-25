package com.jz.java.designpattern.factory.simplefactory;

public class Entry {

    public static void main(String[] args) {
        double num1 = 20;
        double num2 = 15;
        Operation operation = OperationFactory.getOperation("%", num1, num2);
        double result = operation.getResult();
        System.out.println(result);

        operation = OperationFactory.getOperation("-", num1);
        result = operation.getResult();
        System.out.println(result);
    }
}
