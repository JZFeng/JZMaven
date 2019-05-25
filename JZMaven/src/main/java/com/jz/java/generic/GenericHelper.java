package com.jz.java.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GenericHelper {
  public static Type[] getParameterizedTypesOfSuperClass(Class<?> clazz) {
    Type[] results = null;

    Type type = clazz.getGenericSuperclass();
    if(type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;

      results = parameterizedType.getActualTypeArguments();
    }

    return results;
  }

  public static <T> void copy(List<? super T > dest, List<? extends T> src) throws Exception {

    ListIterator di = dest.listIterator();
    ListIterator si = src.listIterator();

    while(si.hasNext()) {
      di.add(si.next());
    }

  }

}
