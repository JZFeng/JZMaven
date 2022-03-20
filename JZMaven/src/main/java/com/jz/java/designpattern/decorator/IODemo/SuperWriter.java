package com.jz.java.designpattern.decorator.IODemo;

import java.io.Writer;
import java.io.InputStream;

public abstract class SuperWriter extends Writer {
    protected Writer out;

    protected SuperWriter(Writer out) {
        super(out);
        this.out = out;
    }
}
