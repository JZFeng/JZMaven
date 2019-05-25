package com.jz.java.designpattern.factory.abstractfactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Entry {



  public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    double num1 = 5.2;
    double num2 = 2.3;

    //User input, operator, num1, num2;
    Operation operation = Factory.createOpeartion("%", num1, num2);
    double result = operation.getResult();
    System.out.println(result);

  }




}
