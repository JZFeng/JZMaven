package com.jz.java.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerDemo {

    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.WARNING);
        logger.info("New a person");

        try {
            new Person(null);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Fail to create a Person", e);
        }
        logger.info("End program.");

    }


}
