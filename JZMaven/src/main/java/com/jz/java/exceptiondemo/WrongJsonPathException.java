package com.jz.java.exceptiondemo;

public class WrongJsonPathException extends BaseException {

    public WrongJsonPathException() {
        super();
    }

    public WrongJsonPathException(String message) {
        super(message);
    }

    public WrongJsonPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongJsonPathException(Throwable cause) {
        super(cause);
    }
}
