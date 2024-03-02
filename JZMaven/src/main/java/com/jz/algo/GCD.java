package com.jz.algo;

public class GCD {

    public static void main(String[] args) {
        System.out.println(gcd(123456, 7890));
    }
    //辗转相除法，欧几里得公式;
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    public static void swap(long a, long b) {
        if (a < b) {
            long tmp = b;
            b = a;
            a = tmp;
        }
    }
}
