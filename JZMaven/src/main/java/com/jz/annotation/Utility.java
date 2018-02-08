package com.jz.annotation;

@Description(value = "This is a very useful utility class.")
public class Utility {
    @Author(name = "JZ", group = "QE")
    public String work() {
        return "work over";
    }
}
