package com.jz.Enum;

import java.io.IOException;

public class EnumDemo {

    public static void main(String[] args) throws IOException {
        System.out.print("Please enter a number : ");
        int today = System.in.read();
        Weekday td = null;
        switch (today) {
            case 49: {
                td = Weekday.MON;
                break;
            }

            case 50: {
                td = Weekday.TUE;
                break;
            }
            case 51: {
                td = Weekday.WED;
                break;
            }
            case 52: {
                td = Weekday.THUR;
                break;
            }
            case 53: {
                td = Weekday.FRI;
                break;
            }
            case 54: {
                td = Weekday.SAT;
                break;
            }
            case 56: {
                td = Weekday.SUN;
                break;
            }
        }

        switch (td) {

            case MON: {
                System.out.println("today is Monday");
                break;
            }
            case TUE: {
                System.out.println("today is Tuesday");
                break;
            }
            case WED: {
                System.out.println("today is Wednesday");
                break;
            }
            case THUR: {
                System.out.println("today is Thursday");
                break;
            }

            case FRI: {
                System.out.println("today is Friday");
                break;
            }

            case SAT: {
                System.out.println("today is Saturday");
                break;
            }
            case SUN: {
                System.out.println("today is Sunday");
                break;
            }
        }
    }

}
