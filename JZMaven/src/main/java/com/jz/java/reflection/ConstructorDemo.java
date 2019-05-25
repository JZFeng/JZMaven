package com.jz.java.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ConstructorDemo {
  public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Class clazz = Student.class;
    Constructor constructor = clazz.getDeclaredConstructor(String.class, int.class);
    printConstructor(constructor);

    constructor.setAccessible(true);
    Student student = (Student)constructor.newInstance("JZ", 20);
    student.hello();


  }

  public static void printConstructor(Constructor constructor) {
    System.out.println(constructor);
    System.out.println(constructor.getName());
    System.out.println(Arrays.asList(constructor.getParameters()));
    System.out.println(Arrays.asList(constructor.getModifiers()));
  }
}
