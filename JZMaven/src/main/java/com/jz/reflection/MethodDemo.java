package com.jz.reflection;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodDemo {
  public static void main(
      String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IntrospectionException {
    Student student = new Student("Jason");
    Class clazz = student.getClass();

    Method method = clazz.getMethod("getAddress");
    System.out.println(method.invoke(student));

    method = clazz.getMethod("setAddress", String.class);
    method.invoke(student, "Chongqing");
    System.out.println(student.getAddress());

    method = student.getClass().getMethod("staticMethod");
    method.setAccessible(true);
    method.invoke(null);

    Person person = student;
    person.hello();


    BeanInfo bi = Introspector.getBeanInfo(Student.class);
    for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
      if (pd.getName().equals("class")) {
        continue;
      }
      printMethod(pd.getReadMethod());
      printMethod(pd.getWriteMethod());
    }
  }


  public static void printMethod(Method method) {
    System.out.println(method);
    System.out.println("Method name : " + method.getName());
    System.out.println("Return type : " + method.getReturnType());
    System.out.println("Parameters : " + Arrays.toString(method.getParameters()));
    System.out.println(
        "Parameter types : " + Arrays.toString(method.getParameterTypes()));
    System.out.println("Parameter types : " + method.getModifiers());


  }
}

