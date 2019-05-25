package com.jz.java.control;

public class IfDemo {
    public static void main(String[] args) {

        // if float and double
        double d = 1 - 9.0 / 10;
        if (Math.abs(0.1 - d) < 0.0000001) {
            System.out.println("d equals 0.1 ");
        }

        // if String
        String s1 = "Hello";
        String s2 = "Hello";
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));

        if(s1 != null && s2 != null && s1.equals(s2)) {
            System.out.printf("Two equal strings.");
        }

    }
}
