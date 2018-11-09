package com.jz.collection;

import java.util.ArrayList;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Zhu", 20));
        persons.add(new Person("Luo", 22));
        persons.add(new Person("Wang", 21));


        System.out.println(persons.contains(new Person("Zhu", 20)));
        System.out.println(persons.indexOf(new Person("Wang", 21)));

    }

}
