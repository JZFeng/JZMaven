package com.jz.java.designpattern.factory;

import static com.jz.java.designpattern.factory.ConfigUtils.readConfigFromFile;

public class Entry {

  public static void main(String[] args) {
    String filename = "DBConfig.json";
    String configFileContent = readConfigFromFile(filename);

    IConfigParser parser = ParserFactory.createParser(filename);
    parser.parse(configFileContent);
  }


}
