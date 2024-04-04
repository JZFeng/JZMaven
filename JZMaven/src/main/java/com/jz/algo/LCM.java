package com.jz.algo;

import static com.jz.algo.GCD.gcd;

public class LCM {
    public static void main(String[] args) {
        System.out.println(LCM(10086, 328));
    }
    public static long LCM(long a, long b) {
        return  a / gcd(a,b) * b;
    }
}
