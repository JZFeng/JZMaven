package com.jz.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Person {
    private String name;
    private final Log log = LogFactory.getLog(getClass());

    Person(String name) {
        log.info("Creating a new person.");
        if(name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hello, " + name + "!";
    }
}
