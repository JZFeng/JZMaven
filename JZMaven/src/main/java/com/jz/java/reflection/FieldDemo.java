package com.jz.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldDemo {
    public static void main(
            String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {


        Class<? extends Object> clazz = null;

        Student student = new Student("JZ");
        Class<? extends Student> clz = student.getClass();
        Field field = student.getClass().getDeclaredField("number");
        field.set(null, 10);
        System.out.println(field.get(student));
/*
        clazz = student.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields ) {
            printField(field);
        }

        student.hello();
*/


      /*  boolean isStudentClass = true;
        if(isStudentClass) {
            clazz = Class.forName("com.jz.java.reflection.Student");
        } else {
            clazz = Class.forName("com.jz.java.reflection.Teacher");
        }

        System.out.println(clazz.getName());
        System.out.println(clazz.getSimpleName());
        System.out.println(clazz.isInterface());
        System.out.println(clazz.isEnum());
        System.out.println(clazz.getPackage().getName());
        System.out.println(clazz.newInstance());*/
    }

    public static void printField(Field field) {
        System.out.println("****************************");
        System.out.println("Field name : " + field.getName());
        System.out.println("Field type : " + field.getType());
        System.out.println("Field modifers : " + field.getModifiers());
        System.out.println("isPublic? " + Modifier.isPublic(field.getModifiers()));
        System.out.println("isProtected? " + Modifier.isProtected(field.getModifiers()));
        System.out.println("isPrivate? " + Modifier.isPrivate(field.getModifiers()));
        System.out.println("isStatic? " + Modifier.isStatic(field.getModifiers()));
        System.out.println("isFinal? "  + Modifier.isFinal(field.getModifiers()));
    }


}
