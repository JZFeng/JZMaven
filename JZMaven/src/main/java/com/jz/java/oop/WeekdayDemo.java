package com.jz.java.oop;

public class WeekdayDemo {
    public static void main(String[] args) {
       Weekday[] weekdays =  Weekday.values();
       for(Weekday weekday : weekdays) {
           System.out.println(weekday.name());
       }

       System.out.println(Weekday.valueOf("FRI"));
       System.out.println(Weekday.valueOf("SAT").ordinal());

       Weekday fri = Weekday.FRI;
       System.out.println(fri.toChinese());


    }
}
