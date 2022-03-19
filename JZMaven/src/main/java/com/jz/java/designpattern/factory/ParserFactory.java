package com.jz.java.designpattern.factory;

import static com.jz.java.designpattern.factory.ConfigUtils.getFileExtension;

public class ParserFactory {

  public static IConfigParser createParser(String configFilePath) {
    IConfigParser parser = null;
    String fileExtension = getFileExtension(configFilePath).toUpperCase();

    try {
      parser = (IConfigParser) Class.forName("com.jz.java.designpattern.factory." + fileExtension + "Parser").newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return parser;
  }


}
