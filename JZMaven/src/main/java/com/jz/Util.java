package com.jz;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static Map<String, String> map = new HashMap<>();
    static {
        map.put("prod", "https://www.ebay.com" );
        map.put("preprod", "https://www.latest.ebay.com" );
        map.put("staging", "https://www.qa.ebay.com" );
    }

    public static void main(String[] args) {
        final int a = 0b111;
        final double TAX_RATE = 0.2d;
        System.out.println(TAX_RATE);
        long l = 9000_000_000_000L;
        System.out.println(Long.toHexString(l));
        System.out.println(Long.toBinaryString(l));
    }


    public static boolean isClassPresent(String name) {
        try{
            Class.forName(name);
            return true;
        }  catch(ClassNotFoundException e) {
            return false;
        }
    }
}