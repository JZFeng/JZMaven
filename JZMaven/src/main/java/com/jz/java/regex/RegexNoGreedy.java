package com.jz.java.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexNoGreedy {

    public static void main(String[] args) {
        System.out.println(numOfZeroes("123000"));
        System.out.println(numOfZeroes("1000"));
        System.out.println(numOfZeroes("0000"));
        System.out.println(numOfZeroes("101000"));
    }

    public static int numOfZeroes(String str ) {
        if(str == null || str.length() == 0) {
            return 0;
        }
        Pattern pattern = Pattern.compile("^\\d+?(0*)$");
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()) {
            return matcher.group(1).length();
        }

        return 0;
    }
}
