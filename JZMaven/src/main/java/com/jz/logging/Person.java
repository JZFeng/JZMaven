package com.jz.logging;

public class Person extends BaseClass {
    private String name;

    Person(String name) {
        log.info("Creating a new person");
        if(name == null) {
          throw new IllegalArgumentException(name);
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hello, " + name + "!";
    }
}
