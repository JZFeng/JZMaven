package com.jz.designpattern.Singleton;

public class Entry {
  public static void main(String[] args) {
    Singleton instance = Singleton.getInstance();
    System.out.println(instance);
  }
}
