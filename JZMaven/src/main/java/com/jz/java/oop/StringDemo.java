package com.jz.java.oop;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringDemo {
    public static void main(String[] args) {
        String str = "Hello World";
        System.out.println(str.substring(1));
        System.out.println(str.substring(1,3));

        str = "中文ABC";
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);


    }
}
