package com.jz.oop;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class UtilsDemo {
    public static void main(String[] args) {

        Random random = new Random(12345);
        for (int i = 0; i < 10; i++) {
            System.out.print(random.nextInt() + " ");
        }


        int max = 100;
        int min = 90;
        for (int i = 0; i < 10; i++) {
            System.out.println((int) (Math.random() * (max - min)) + min);
        }

        System.out.println(Math.PI);
        System.out.println(Math.E);


        SecureRandom sr = new SecureRandom();
        for(int i = 0 ; i < 10 ; i++) {
            System.out.print(sr.nextInt(20) + " " );
        }

        System.out.println("");

        BigInteger bi = new BigInteger("1234567");
        System.out.println(bi.pow(5));

        BigDecimal bd = new BigDecimal("121.345");
        System.out.println(bd.multiply(bd));
    }
}
