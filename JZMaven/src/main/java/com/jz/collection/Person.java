package com.jz.collection;

import java.util.Objects;

public class Person {
    public String name;
    public int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name : " + name + " ; Age : " + age;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Person) {
            Person p = (Person) obj;
            return Objects.equals(this.name, p.name) && this.age == p.age;
        }

        return false;


    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.age);
    }
}
