package com.jz.designPattern.decorator.dress;

public class Entry {
    public static void main(String[] args) {
        Person person = new Person("JZ");
        IPerson p = new Jeans(new Sunglass( new Tie(person)));
        System.out.println(person.getName() + " is wearing : ");
        System.out.println(p.getClothes().trim());
    }

}
