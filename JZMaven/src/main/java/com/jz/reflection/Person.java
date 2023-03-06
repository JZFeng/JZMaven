package com.jz.reflection;

public class Person implements Hello , Cloneable{
    public int age;

    public Person() {
        this(1);
    }

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void hello() {
        System.out.println("Hi");
    }
}
