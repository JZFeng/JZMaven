package com.jz.java.oop;

public class StringBuilderDemo {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.append("Hello").append(" World").insert(0, "JZ,").toString());
    }
}
