package com.jz.oop;

public class AbstractDemo {
    public static void main(String[] args) {
        Shape s1 = new Rectangle(3,2);
        Shape s2 = new Circle(3.5);

        System.out.println(ShapeUtil.isGreaterThan(s1, s2));
        System.out.println(s1.area() + s2.area());
        System.out.println(ShapeUtil.sum(new Shape[]{s1,s2}));
    }
}
