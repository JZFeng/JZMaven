package com.jz.designPattern.decorator.dress;

public class Person implements IPerson {
    String name ;

    Person(String name) {
        this.name = name;
    }


    @Override
    public String getClothes() {
        return "";
    }

    public String getName() {
        return name;
    }


}
