package com.jz.designpattern.proxy.metriccollector;

public class Entry {
  public static void main(String[] args) {
    IUserController userController = new UserController(); //ProxiedObject
    MetricsCollectorProxy proxy = new MetricsCollectorProxy();
    proxy.createProxy(userController);


  }
}
