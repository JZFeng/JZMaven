package com.jz.java.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeDemo {
    public static void main(String[] args) {
        LocalDate d1 = LocalDate.now();
        System.out.println(d1.toEpochDay() + " ; " + d1.toString());
        System.out.println(d1.getDayOfWeek().getValue() + " ; " + d1.getDayOfMonth() + "; " + d1.getDayOfYear());
        System.out.println(System.currentTimeMillis());

        LocalDate d2 = LocalDate.of(2018,11,19);
        System.out.println(d2);

        System.out.println(d2.minusYears(1));
        System.out.println(d2.plusMonths(1));
        System.out.println(d2);


        LocalTime t1 = LocalTime.now();
        System.out.println(t1);
        LocalTime t2 = LocalTime.of(7,04,15);
        System.out.println(t2);


        LocalDateTime dt1 = LocalDateTime.now();
        System.out.println(dt1);
        LocalDateTime dt2 = LocalDateTime.of(d2, t2);
        System.out.println(dt2 + "\r\n");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));
        System.out.println(dtf.parse("2018-11-19 20:21:22"));
        System.out.println(LocalDateTime.parse("2018-11-19T20:21:22"));
        System.out.println(LocalDateTime.parse("2018-11-19 20:21:22", dtf));



    }
}
