package com.jz.designpattern.decorator.dress;

public class Entry {
    public static void main(String[] args) {
        Person person = new Person("JZ");
        IPerson p = new Jeans(new Sunglass( new Tie(person)));
        System.out.println(person.getName() + " is wearing : ");
        System.out.println(removeLastAddSign(p.getClothes()));
    }

    private static String removeLastAddSign(String str) {
        if(str == null || str.length() == 0) {
            return "";
        }

        int index = str.lastIndexOf("+");
        str = str.substring(0, index).trim();

        return str;

    }
}
