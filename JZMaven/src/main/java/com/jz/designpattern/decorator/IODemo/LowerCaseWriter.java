package com.jz.designpattern.decorator.IODemo;

import java.io.IOException;
import java.io.Writer;

public class LowerCaseWriter extends DecoratorWriter {
    public LowerCaseWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(int c) {
        // TODO Auto-generated method stub
        try {
            out.write(Character.toLowerCase(c));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
