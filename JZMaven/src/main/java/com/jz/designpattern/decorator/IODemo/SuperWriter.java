package com.jz.designpattern.decorator.IODemo;

import java.io.Writer;

public abstract class SuperWriter extends Writer {
    protected Writer out;

    protected SuperWriter(Writer out) {
        super(out);
        this.out = out;
    }
}
