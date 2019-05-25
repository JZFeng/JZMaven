package com.jz.java.designpattern.Singleton;

public class Singleton {
  //Do not forget volatile keyword;
  private static volatile Singleton instance = null;

  //For testing purpose;
  private static int num;

  //private constructor;
  private Singleton() {
    num++;
  }

  public static Singleton getInstance() {
    if(instance == null) {
      synchronized (Singleton.class) {
        if(instance == null) {
          instance = new Singleton();
        }
      }
    }

    return instance;
  }

  @Override
  public String toString() {
    return "Number of instance : " + num;
  }
}
