package com.jz.designpattern.factory;

import static com.jz.designpattern.factory.ConfigUtils.readConfigFromFile;

public class Entry {

  public static void main(String[] args) {
    String filename = "DBConfig.json";
    String configFileContent = readConfigFromFile(filename);

    IParser parser = ParserFactory.createParser(filename);
    parser.parse(configFileContent);
  }


}
