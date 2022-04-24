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

        Map<String, Integer> prices = new HashMap<>();
        // 往HashMap中添加映射项
        prices.put("Shoes", 200);
        prices.put("Bag", 300);
        prices.put("Pant", 150);
        System.out.println("HashMap: " + prices);

        // 计算 Shirt 的值
        int shirtPrice = prices.computeIfAbsent("Shirt", key -> 280);
        System.out.println("Price of Shirt: " + shirtPrice);

        // 输出更新后的HashMap
        System.out.println("Updated HashMap: " + prices);

    }


}
