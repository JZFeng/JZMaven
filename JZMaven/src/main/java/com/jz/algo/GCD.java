package com.jz.algo;

public class GCD {
    //辗转相除法，欧几里得公式;
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }
}
