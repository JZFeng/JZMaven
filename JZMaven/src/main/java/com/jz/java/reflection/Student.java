package com.jz.java.reflection;

import java.io.Serializable;

public class Student extends Person implements Hello, Serializable {

    public static int number = 0;

    protected String name;

    private int age;

    private String address = "beijing";

    Student() {
        this("unnamed");
    }

    Student(String name) {
        this.name = name;
        number++;
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
        number++;
    }

    @Override
    public void hello() {
        System.out.println("Hi, " + name + " from " + address + "!");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static void staticMethod() {
        System.out.println("You are calling static method");
    }
}
