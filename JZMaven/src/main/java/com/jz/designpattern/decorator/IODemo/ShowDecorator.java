package com.jz.designpattern.decorator.IODemo;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class ShowDecorator {
    public static void main(String[] args) {
        try {
            FileWriter write = new FileWriter("test.txt");
            BufferedWriter bw = new BufferedWriter(write);
            bw.write("hello,LGH ,my name is Liuguohua");
            bw.close();
            write.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
