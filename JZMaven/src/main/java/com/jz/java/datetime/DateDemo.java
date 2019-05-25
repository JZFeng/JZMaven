package com.jz.java.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateDemo {
    public static void main(String[] args) throws ParseException {
        System.out.println(System.currentTimeMillis());

        Date now = new Date();
        System.out.println(now);
        long epotchTime = now.getTime();
        System.out.println(epotchTime);
        now = new Date(epotchTime);
        System.out.println(now);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(now));

        String s1 = "2018-11-15 14:57:50";
        now = format.parse(s1);
        System.out.println(now);

        String s2 = "Nov/15/2018 15:03:25";
        now = new SimpleDateFormat("MMM/dd/yyyy HH:mm:ss", Locale.US).parse(s2);
        System.out.println(now);



    }
}
