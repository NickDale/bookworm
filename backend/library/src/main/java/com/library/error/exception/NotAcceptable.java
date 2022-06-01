package com.library.error.exception;

public class NotAcceptable extends RuntimeException {

    public NotAcceptable(String message) {
        super(message);
    }

    public NotAcceptable(String message, Throwable ex) {
        super(message, ex);
    }
}
