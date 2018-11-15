package com.jz.oop;

public enum Weekday {
    SUN("星期日"), MON("星期一"), TUE("星期二"), WED("星期三"), THU("星期四"),FRI("星期五"),SAT("星期六"); //enum，相当于定义Weekday的常量。

    private String chineseName;

    Weekday(String chineseName) {
        this.chineseName = chineseName;
    }

    public String toChinese() {return chineseName;}
}
