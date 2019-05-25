package com.jz.java.exceptiondemo;

public class AssertionDemo {
    public static void main(String[] args) {
        double x = abs(-23.44);
        assert x > 0 : "abs(" + x + ") should be greater than 0.";
        System.out.println(x);
    }

    public static double abs(double x ) {
        return (x > 0 ) ? x : -x;
    }
}
