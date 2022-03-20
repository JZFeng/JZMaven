package com.jz.java.designpattern.templateMethod.Callback;

public class Entry {
  public static void main(String[] args) {
    Li li = new Li();
    Wang wang = new Wang(li);
    String question = "1+1 =?";
    wang.askAQuestion(question);
  }
}
