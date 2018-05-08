package com.jz.convert;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConvertChineseNumbertoArabicNumer {

    public static final Map<String, Integer> units = new LinkedHashMap<>();
    public static final Map<String, Integer> numbers = new LinkedHashMap<>();


    static {
        units.put("十", 10);
        units.put("百", 100);
        units.put("千",1000);
        units.put("万", 10000);
        units.put("萬", 10000);

        numbers.put("零", 0);
        numbers.put("一", 1);
        numbers.put("二", 2);
        numbers.put("三", 3);
        numbers.put("四", 4);
        numbers.put("五", 5);
        numbers.put("六", 6);
        numbers.put("七", 7);
        numbers.put("八", 8);
        numbers.put("九", 9);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        File f = new File("/Users/jzfeng/Desktop/songs");
        if(f.isDirectory()) {
            File[] files = f.listFiles();
            for(File file : files) {
                String fileName = file.getAbsolutePath();
                byte[] bs = fileName.getBytes("UTF-8");
                fileName = new String(bs, "GB2312");
                System.out.print(fileName);

            }
        }

    }

    public static int convert(String str) throws UnsupportedEncodingException {
        if(str == null || str.length() == 0) {
            throw new IllegalArgumentException();
        }

        int i = 0;
        for(; i < str.length(); i++) {
            String s = String.valueOf(str.charAt(i));
            byte[] bs = s.getBytes("utf-16");
            System.out.print(new String(bs, "UTF-8"));

            if(isValidUnit(s) || isValidNumber(s)) {
                break;
            }
        }

        System.out.println(i);

        return 0;



    }

    public static boolean isValidUnit(String unit) {
        return units.containsKey(unit);
    }

    public static boolean isValidNumber(String number) {
        return numbers.containsKey(number);
    }



}
