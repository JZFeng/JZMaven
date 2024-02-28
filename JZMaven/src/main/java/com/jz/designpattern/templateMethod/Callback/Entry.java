package com.jz.designpattern.templateMethod.Callback;

public class Entry {
    public static void main(String[] args) {
        Genius li = new Genius();
        Person wang = new Person(li);
        String question = "1+1 =?";
        wang.ask(question);
    }
}
