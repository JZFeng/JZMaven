package com.jz.java.collection;


import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {

        Map<Person, Integer> map = new HashMap<>();
        map.put(new Person("Zhu", 20), 20);
        map.put(new Person("Xia", 21), 21);
        map.put(new Person("Lin", 18), 18);

        System.out.println(map.get(new Person("Zhu", 20)));



    }

}
