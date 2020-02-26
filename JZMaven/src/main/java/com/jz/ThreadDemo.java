package com.jz;

import java.time.LocalDateTime;

public class ThreadDemo implements Runnable {

  String name;

  ThreadDemo(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    System.out.println(LocalDateTime.now() + "Hello, " + this.name);
  }
}
