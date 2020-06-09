package com.jz.java.designpattern.observer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObserverAction {
  private Object target;
  private Method method;

  ObserverAction(Object target, Method method) {
    this.target = target;
    this.method = method;
    this.method.setAccessible(true);
  }

  public void execute(Object event) {
    try {
      method.invoke(target, event);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
