package com.jz.java.io;

import java.util.Scanner;

public class ScannerDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your name: ");
        String name = scanner.nextLine();
        System.out.print("Input your age: ");
        float age = scanner.nextFloat();

        System.out.printf("Hi, %1$s : %s is %.2f years old", name, age);
    }
}
