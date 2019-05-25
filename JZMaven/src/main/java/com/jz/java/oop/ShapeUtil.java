package com.jz.java.oop;

public class ShapeUtil {

    public static double sum(Shape[] shapes) {
        double sum = 0.0d;
        for(Shape shape : shapes) {
            sum += shape.area();
        }

        return sum;
    }

    public static boolean isGreaterThan(Shape s1, Shape s2) {
        return s1.area() > s2.area();
    }



}
