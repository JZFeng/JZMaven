package com.jz.reflection;

import java.util.Arrays;

public class InheritsDemo {
  public static void main(String[] args) {
    Class clazz = Student.class;
    System.out.println(getSuperclass(clazz));
    System.out.println(getAllInterfaces(clazz));
    System.out.println(Person.class.isAssignableFrom(Student.class));
    System.out.println(Student.class.isAssignableFrom(Person.class));

  }

  public static String getSuperclass(Class clazz) {
    StringBuilder sb = new StringBuilder();
    sb.append(clazz.getSimpleName() + " class hierachy: ");
    while (clazz != null) {
      sb.append(clazz.getSimpleName() + "->");
      clazz = clazz.getSuperclass();
    }

    return sb.toString().substring(0, sb.toString().length() - 2);
  }

  public static String getInterfaces(Class clazz) {
    if(clazz == null || clazz.getInterfaces().length == 0) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    sb.append(Arrays.toString(clazz.getInterfaces()));

    return sb.toString().trim();
  }

  public static String getAllInterfaces(Class clazz) {

    StringBuilder sb = new StringBuilder();

    while (clazz != null) {
      sb.append(getInterfaces(clazz));
      clazz = clazz.getSuperclass();
    }


    return sb.toString().trim();
  }

}
