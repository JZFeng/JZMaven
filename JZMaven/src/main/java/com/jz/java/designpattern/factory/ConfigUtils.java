package com.jz.java.designpattern.factory;

class ConfigUtils {
  static String readConfigFromFile(String configFilePath) {
    return configFilePath;
  }

  static String getFileExtension(String configFilePath) {
    if(configFilePath.endsWith("xml")) {
      return "xml";
    }
    if(configFilePath.endsWith("json")) {
      return "json";
    }
    return "";
  }
}
