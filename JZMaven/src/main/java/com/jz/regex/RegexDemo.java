package com.jz.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

    public static void main(String[] args) {
        Object object = new Object();

        String str1 = "The quick\tbrown,fox; jumps over the lazy dog.";
        String[] strs = str1.split("[\\s\\,\\;]+");
        System.out.println(Arrays.toString(strs));

        String str2 = "The quick brown fox jumps over the lazy dog.";
        Pattern pattern =  Pattern.compile("\\w*o\\w*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str2);
        while(matcher.find()) {
            System.out.println("Start:" + matcher.start() + " ; End: " + matcher.end() +"; " + str2.substring(matcher.start(), matcher.end()));
        }

        String str3 = "The    quick\tbrown fox jumps over the lazy dog.";
        System.out.println(str3 = str3.replaceAll("\\s+", " "));
        System.out.println(str3.replaceAll("(\\w+)", "<b>$1</b>"));


    }



    public static boolean isValidQQ(String qq) {
        if (qq == null || qq.length() == 0) {
            return false;
        }

        return qq.matches("[1-9]\\d{4,9}");
    }

    public static boolean isValidId(String id) {
        if(id == null || id.length() == 0 ) {
            return false;
        }

        return id.trim().matches("^[1-9]\\d{16}[1-9X]$");
    }


}
