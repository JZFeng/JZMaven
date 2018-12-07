package com.jz.designPattern.decorator;

public class Person implements IComponent {
    private String name;

    Person(String name) {
        this.name = name;
    }

    @Override
    public void show() {
        System.out.println("Person: " + name + ";");
    }

    @Override
    public String toString() {
        return "Person : " + name + ";";
    }
}
