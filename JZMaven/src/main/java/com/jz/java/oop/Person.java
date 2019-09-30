package com.jz.java.oop;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class Person {

    public static void main(String[] args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class);
        PropertyDescriptor[] pds =  beanInfo.getPropertyDescriptors();
        for(PropertyDescriptor pd : pds) {
            printPropertyDescriptor(pd);
        }
    }

    private String name;
    private int age;
    private boolean child;
    private boolean gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isChild() {
        return child;
    }

    public void setChild(boolean child) {
        this.child = child;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    private static void printPropertyDescriptor(PropertyDescriptor pd ) {
        if(pd == null) {
            return;
        }

        Class<?> clazz = pd.getPropertyType();
        Method read = pd.getReadMethod();
        Method write = pd.getWriteMethod();
        String name = pd.getName();

        if(clazz == null || name.equals("class") ) {
            return;
        }


        System.out.println("=======Property Descriptor=======");
        System.out.println("Name: " + name);
        System.out.println("Read Method: " + (read == null ? "null" : read.getName()));
        System.out.println("Write Method: " + (write == null ? "null" : write.getName()));
        System.out.println("Type:  " + clazz.getName());
    }
}
