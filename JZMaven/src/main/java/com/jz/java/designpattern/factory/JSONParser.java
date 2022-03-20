package com.jz.java.designpattern.factory;

class JSONParser implements IConfigParser {
    @Override
    public void parse(String configtext) {
        System.out.println("parse json config file.");
    }

    JSONParser() {
    }
}