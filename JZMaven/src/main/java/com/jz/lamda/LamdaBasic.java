package com.jz.lamda;

import java.util.Arrays;

public class LamdaBasic {
    public static void main(String[] args) {
        String[] words = "Hello World This Is a Lamda Demo".split(" ");
        Arrays.sort(words, String::compareToIgnoreCase);
        Arrays.stream(words).map(String::toLowerCase).forEach(System.out::println);
    }
}
