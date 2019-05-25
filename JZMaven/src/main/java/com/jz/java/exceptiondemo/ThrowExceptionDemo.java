package com.jz.java.exceptiondemo;

public class ThrowExceptionDemo {
    public static void main(String[] args) throws Exception {
        process1("ABC");
//        process2("ABC");
    }

    public static void process1(String str) throws Exception {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            for (Throwable t : e.getSuppressed()) {
                t.printStackTrace();
            }
            throw new IllegalArgumentException(e);
        } finally {
            System.out.println("finally...");
        }
    }

    public static void process2(String str) throws Exception {
        Exception origin = null;
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException e) {
            origin = e;
            throw new IllegalArgumentException(e);
        } finally {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                if (origin != null) {
                    origin.addSuppressed(e);
                } else {
                    origin = e;
                }
            }

            if (origin != null) {
                throw origin;
            }
        }
    }

}
