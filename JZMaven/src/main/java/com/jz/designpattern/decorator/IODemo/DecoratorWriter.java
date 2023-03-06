package com.jz.designpattern.decorator.IODemo;

import java.io.IOException;
import java.io.Writer;

public abstract class DecoratorWriter extends SuperWriter
{
    public DecoratorWriter(Writer out)
    {
        super(out);
    }

    @Override
    public void write(String s)
    {
        char[] cs = s.toCharArray();
        for (char cc : cs)
        {
            write(cc);
        }
    }

    public abstract void write(int c);

    @Override
    public void close() throws IOException
    {
        // TODO Auto-generated method stub
        out.close();
    }

    @Override
    public void flush() throws IOException
    {
        // TODO Auto-generated method stub
        out.flush();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException
    {
        // TODO Auto-generated method stub
        for (int i = off; i < len; i++)
        {
            write(cbuf[i]);
        }
    }
}
