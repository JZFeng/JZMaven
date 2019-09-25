package com.jz.java.lamda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class LamdaBasic {
    public static void main(String[] args) {
        String[] words = "Hello World This Is a Lamda Demo".split(" ");
        Arrays.sort(words, String::compareToIgnoreCase);
    }
}
