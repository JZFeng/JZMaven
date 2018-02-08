package com.jz.annotation;

@Description(value = "这是一个有用的工具类")
public class Utility {
    @Author(name = "JZ", group = "QE")
    public String work() {
        return "work over";
    }
}
