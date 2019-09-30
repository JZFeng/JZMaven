package com.jz.java.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
  public static void main(String[] args) throws IOException {
    InputStream in = Main.class.getResourceAsStream("data.txt");
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int n ;
    while((n =  in.read(buffer)) != -1 ) {
      out.write(buffer, 0,n);
    }


  }
}
