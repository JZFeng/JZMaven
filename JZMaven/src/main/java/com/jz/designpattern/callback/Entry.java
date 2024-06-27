package com.jz.designpattern.callback;

public class Entry {
    public static void main(String[] args) {
        Genius li = new Genius();
        Person wang = new Person("Wang");
        String question = "1+1 =?";
        wang.ask(question, li);
    }
}
