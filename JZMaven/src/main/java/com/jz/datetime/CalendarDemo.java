package com.jz.datetime;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarDemo {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());
        System.out.println(calendar.getTimeInMillis());
        System.out.println(calendar.getTimeZone());

        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.get(Calendar.MINUTE));
        System.out.println(calendar.get(Calendar.SECOND));
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println(calendar.get(Calendar.MILLISECOND));

        calendar.clear();
        calendar.set(Calendar.YEAR, 1999);
        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        calendar.set(Calendar.DAY_OF_MONTH,6);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        calendar.add(Calendar.HOUR_OF_DAY, 16);
        calendar.add(Calendar.MINUTE, 16);
        System.out.println(calendar.getTime());



       /* String[] timezones = TimeZone.getAvailableIDs();
        for(String str : timezones) {
            System.out.println(str + " ");
        }*/
    }
}
