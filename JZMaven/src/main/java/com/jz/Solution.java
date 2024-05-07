package com.jz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
class Dummy{}

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.numberToWords(Integer.MAX_VALUE));
    }


    public String numberToWords(int num) {
        // edge case
        if (num == 0){
            return "Zero";
        }

        return helper(num).trim();
    }

    //用String比较方便;
    String[] ones = {"", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"};
    String[] tens = {"", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};
    String[] thousands = {"", " Thousand", " Million", " Billion"};
    //helper function
    public String helper(int n) {
        //三位数的结果;
        if (n < 20)  return ones[n];
        if (n < 100) return tens[n / 10] + helper(n % 10);
        if (n < 1000) return helper(n / 100) + " Hundred" + helper(n % 100);

        for (int i = 3; i >= 0; i--) {
            if (n >= Math.pow(1000, i)) {
                return helper((int)(n / Math.pow(1000, i))) + thousands[i] + helper((int)(n % Math.pow(1000, i)));
            }
        }

        return "";
    }
}

