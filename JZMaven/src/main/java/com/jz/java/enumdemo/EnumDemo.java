package com.jz.java.enumdemo;

import java.util.Scanner;

import static com.jz.java.enumdemo.Weekday.*;

public class EnumDemo {

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a number: ");
        int num = scanner.nextInt();
        Weekday weekday = null;
        switch(num) {
            case 1 :
                weekday = MON;
                break;
            case 2 :
                weekday = TUE;
                break;
            case 3 :
                weekday = WED;
                break;
            case 4 :
                weekday = THU;
                break;
            case 5 :
                weekday = FRI;
                break;
            case 6 :
                weekday = SAT;
                break;
            case 7 :
                weekday = SUN;
                break;
            default :
                System.out.println("Please enter a correct number;");
        }
        if(weekday != null) {
            System.out.println(weekday.name() + " : " + weekday.toChinese());
        }

        switch(weekday) {
            case SAT:
            case SUN:
                System.out.println("This is a weekend day!");
        }
    }

}
