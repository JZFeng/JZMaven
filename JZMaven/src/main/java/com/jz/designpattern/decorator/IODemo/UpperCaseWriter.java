package com.jz.designpattern.decorator.IODemo;

import java.io.IOException;
import java.io.Writer;


public class UpperCaseWriter extends DecoratorWriter {

    public UpperCaseWriter(Writer out) {
        super(out);
    }

    public void write(int c) {
        // TODO Auto-generated method stub
        try {
            out.write(Character.toUpperCase(c));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
