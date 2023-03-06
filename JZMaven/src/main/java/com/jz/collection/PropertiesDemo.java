package com.jz.collection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.properties");
        properties.load(in);
        String url = properties.getProperty("url");
        String lan = properties.getProperty("lan");
        String courseTitle = properties.getProperty("course.title");
        String SHIPPINGSERVICE = properties.getProperty("SHIPPINGSERVICE");
        System.out.println(SHIPPINGSERVICE);
        System.out.println(url + " ; " + lan + " ; " + courseTitle);
        System.out.println(properties.getProperty("desc", "default desc"));
    }

}
