package com.jz.java.designpattern.decorator.coffee;

public class Entry {
    public static void main(String[] args) {
        ICoffee coffee = new Tea(new Sugar(new Sugar( new Cream(new PlainCoffee()))));
        System.out.println(removeLastAddSign(coffee.getIngredients()));
        System.out.println(coffee.getPrice());
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
