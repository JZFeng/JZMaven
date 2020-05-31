package com.jz.java.designpattern.factory;

public class XMLParser implements IConfigParser {
  @Override
  public void parse(String configtext) {
    System.out.println("Prase XML file");
  }
}
