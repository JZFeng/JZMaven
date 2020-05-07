package com.jz.java.designpattern.factory.factorymethod;

import java.io.File;

public class Entry {
  public static void main(String[] args) {
    File file = new File("/Users/jzfeng/Documents/网盘下载/机器人总动员.mp4");
    System.out.println("File size : " + file.length());
  }

}
