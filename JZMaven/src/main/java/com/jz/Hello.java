package com.jz;

import java.io.*;

public class Hello {
    public static void main(String[] args) {
        final int a = 0b111;
        final double TAX_RATE = 0.2d;
        System.out.println(TAX_RATE);
        long l = 9000_000_000_000L;
        System.out.println(Long.toHexString(l));
        System.out.println(Long.toBinaryString(l));
    }
}