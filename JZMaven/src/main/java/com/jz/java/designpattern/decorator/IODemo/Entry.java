package com.jz.java.designpattern.decorator.IODemo;

import java.io.FileWriter;
import java.io.IOException;


public class Entry {
    public static void main(String[] args) {
        try {
            UpperCaseWriter caseWriter = new UpperCaseWriter(new FileWriter(
                    "hah.txt"));
            caseWriter.write("HELLO,LGH,hello lgh");
            caseWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
