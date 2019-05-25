package com.jz.java.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLoggingDemo {

    private static Log log = LogFactory.getLog(CommonsLoggingDemo.class);

    public static void main(String[] args) {
        Person person = new Person("Jason");
        log.info(person.toString());

        try{
            new Person(null);
        } catch(IllegalArgumentException e) {
            log.fatal("Person name cannot be null" , e);
        }
    }
}
