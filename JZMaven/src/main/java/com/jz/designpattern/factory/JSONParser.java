package com.jz.designpattern.factory;

class JSONParser implements IParser {
    @Override
    public void parse(String configtext) {
        System.out.println("parse json config file.");
    }

    JSONParser() {
    }
}