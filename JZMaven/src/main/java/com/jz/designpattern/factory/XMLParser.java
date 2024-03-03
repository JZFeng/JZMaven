package com.jz.designpattern.factory;

public class XMLParser implements IParser {
    @Override
    public void parse(String configtext) {
        System.out.println("Parse XML file");
    }

    XMLParser() {
    }

}
