package main.java.com.jz.collection;

import java.util.*;

public class SetDemo {
    public static void main(String[] args) {
        List<String> strs = Arrays.asList("Pear", "Apple", "Orange", "Banana", "Orange");
        System.out.println(strs);
        List<String> list1 = dedupleList(strs);
        System.out.println(list1);


        List<String> ss = Arrays.asList("abc", "xyz", "abc", "www", "edu", "www", "abc");
        System.out.println(ss);
        List<String> list2 = dedupleList2(ss);
        System.out.println(list2);
    }

    private static List<String> dedupleList(List<String> list) {
        Set<String> set = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return -o1.compareTo(o2);
            }
        });

        set.addAll(list);

        return new ArrayList<String>(set);

    }


    private static List<String> dedupleList2(List<String> list ) {
        Set<String> set = new LinkedHashSet<>(list);
        return new ArrayList<>(set);
    }
}
