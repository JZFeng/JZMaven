package com.jz.collection;

import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;

public class PropertiesDemo {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
//        properties.load(new FileInputStream("/Users/jzfeng/git/JZMaven/JZMaven/src/main/java/com/jz/collection/settings.properties"));

        properties.load(PropertiesDemo.class.getResourceAsStream("settings.properties"));
        String url = properties.getProperty("url");
        String lan = properties.getProperty("lan");
        String courseTitle = properties.getProperty("course.title");
        System.out.println(url + " ; " + lan + " ; " + courseTitle);
        System.out.println(properties.getProperty("desc", "default desc"));

    }

}
