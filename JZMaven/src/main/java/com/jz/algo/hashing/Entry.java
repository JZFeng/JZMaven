package com.jz.algo.hashing;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Entry
{

    public static void main(String[] args)
    {
        Hashtable<String, Integer> employees = new Hashtable<String, Integer>(7);
        employees.put("Tom", 25);
        employees.put("Jack", 30);
        employees.put("Allen", 18);
        employees.put("Woody", 25);

        printHashtable1(employees);

        
        
        
    }

    
    public static void printHashtable1(Hashtable t)
    {
        Enumeration<String> e = t.keys();

        while (e.hasMoreElements())
        {
            String s = e.nextElement();
            System.out.println(s + " , " + t.get(s));

        }
        
    }
    
    
    public static int hashCode(String s)
    {
        int h = 0;
        char[] values = s.toCharArray();

        for (int i = 0; i < values.length; i++)
        {
            h = h * 31 + values[i];
        }
        return h;
    }

    public static boolean isPrime(int n)
    {
        if (n < 2)
            return false;

        if (n == 2 || n == 3)
            return true;

        if (n % 2 == 0)
            return false;

        for (int i = 3; i * i <= n; i = i + 2)
        {
            if (n % i == 0)
                return false;
        }

        return true;
    }

}
