package com.jz.java.generic;

import java.lang.reflect.Type;

public class GenericTypeErasure {
  public static void main(String[] args) {
    IntPair intPair = new IntPair(30, 70);
    for (Type type : GenericHelper.getParameterizedTypesOfSuperClass(IntPair.class)) {
      System.out.println((Class<?>) type);
    }

  }
}
