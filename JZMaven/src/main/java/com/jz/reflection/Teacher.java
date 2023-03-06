package com.jz.reflection;

public class Teacher implements Hello {
    private String name;

    Teacher() {
        this("unnamed");
    }

    Teacher(String name) {
        this.name = name;
    }

    @Override
    public void hello() {
        System.out.println("Solution, " + name + "!");
    }
}
