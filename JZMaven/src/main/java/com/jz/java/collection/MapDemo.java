package com.jz.java.collection;


import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {

        Map<Person, Integer> hashmap = new HashMap<>();
        hashmap.put(new Person("Zhu", 20), 20);
        hashmap.put(new Person("Xia", 21), 21);
        hashmap.put(new Person("Lin", 18), 18);

        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        String val = map.putIfAbsent("b","v");
        String val4 = map.putIfAbsent("c","v");
        String val1 = map.compute("b", (k, v) -> { return map.getOrDefault(k, "") + "xx"; }); // 输出 v
        String val2 = map.computeIfAbsent("b", k -> {return "value";} );
        String val3 = map.computeIfPresent("b", (k,v) -> {return map.getOrDefault(k,"") + "yy";});
    }

}
