package com.jz.java.logging;


public class CommonsLoggingDemo extends BaseClass {

    public static void main(String[] args) {
        Person person = new Person("Jason");
        log.info(person.toString());
        try{
            new Person(null);
        } catch(IllegalArgumentException e) {
            log.fatal("name cannot be null", e);
        }
    }
}
