package com.jz.java.collection;

import java.util.Iterator;

public class IteratorDemo {
    public static void main(String[] args) {
        ReadOnlyList<String> readOnlyList = new ReadOnlyList("Tom", "Jack", "Helen");
        Iterator<String> iterator = readOnlyList.iterator();

        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        for(String str : readOnlyList) {
            System.out.println(str);
        }

        for(Iterator<String> itr = readOnlyList.iterator(); itr.hasNext();) {
            System.out.println(itr.next());
        }
    }

}
