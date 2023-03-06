package com.jz.datetime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTimeCalculate {
    public static void main(String[] args) {
        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
        System.out.println(firstDay);
        LocalDate firstDay2 = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        System.out.println(firstDay.equals(firstDay2));

        System.out.println(LocalDate.now().withMonth(1));
        LocalDate firstSaturday = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.SATURDAY));
        System.out.println(LocalDate.now().isAfter(firstSaturday));
        System.out.println("Leap Year:  " + firstSaturday.isLeapYear());


        System.out.println( LocalDate.of(2050, 3,25).toEpochDay() - LocalDate.now().toEpochDay() );
        Period p = LocalDate.now().until(LocalDate.of(2050,1,19));
        System.out.println(p);
        System.out.println(p.getYears() + " Years " + p.getMonths() + " Months " + p.getDays() + " Days" );


    }
}

