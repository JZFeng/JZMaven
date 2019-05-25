package com.jz.java.control;

import java.util.Arrays;
import java.util.Scanner;

public class SwitchDemo {

    public static void main(String[] args) {

        int[] nums = new int[]{1, 3, 5, 7, 9};
        System.out.println(Arrays.toString(nums));

        int[][] ns = new int[][]{
                {1,3,5},
                {2,4,6},
                {3,6}
        };

        System.out.println(Arrays.deepToString(ns));

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your option: ");
        int option = scanner.nextInt();

        while (option != 0) {

            switch (option) {
                case 1:
                    System.out.println("1 selected");
                    break;
                case 2:
                case 3:
                    System.out.println("2,3 selected");
                    break;
                default:
                    System.out.println("Nothing selected");
            }

            System.out.print("Please enter your option: ");
            option = scanner.nextInt();
        }


    }


}
