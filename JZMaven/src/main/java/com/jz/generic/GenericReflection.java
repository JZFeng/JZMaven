package com.jz.generic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericReflection {
  public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
    Class<String> clazz = String.class;
    String s1 = clazz.newInstance();
    System.out.println(s1);

    Constructor<String> constructor = String.class.getConstructor(String.class);
    s1 = constructor.newInstance("Hello World");
    System.out.println(s1);

//    Pair<String>[] ps = new Pair<String>[2]; //compile error;
    Pair<String>[] pairs = (Pair<String>[])new Pair[2];
    pairs[0] = new Pair<>("a", "b");
    pairs[1] = new Pair<>("1","2");
  }
}
