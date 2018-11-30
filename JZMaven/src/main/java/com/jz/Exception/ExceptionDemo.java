package com.jz.Exception;

import java.io.UnsupportedEncodingException;

public class ExceptionDemo {
    public static void main(String[] args) {
//        getEncoding("UTF-8");
//        getEncoding("ABC");

        proceed("ABC");
    }

    public static void getEncoding(String encoding) {
        System.out.println("Encoding is " + encoding);
        try {
            "test".getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }


    public static void proceed(String str) {
        try {
            int n = Integer.parseInt(str);
            int m = 100 / n;
        } catch (NumberFormatException | ArithmeticException e) {
            System.out.println("Bad Input");
            e.printStackTrace();
        } finally {
            System.out.println("End processing.");
        }
    }
}
