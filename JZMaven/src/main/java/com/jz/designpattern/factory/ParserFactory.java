package com.jz.designpattern.factory;

import java.lang.reflect.Constructor;

import static com.jz.designpattern.factory.ConfigUtils.getFileExtension;

public class ParserFactory {
    public static IConfigParser createParser(String configFilePath) {
        IConfigParser parser = null;
        String fileExtension = getFileExtension(configFilePath).toUpperCase();

        try {
          Class<?> clz = Class.forName("com.jz.designpattern.factory." + fileExtension + "Parser");
          Constructor<?> constructor = clz.getDeclaredConstructor();
          parser = (IConfigParser)constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parser;
    }
}
