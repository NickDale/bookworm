package com.library.error.exception;

public class BadCredentials extends RuntimeException {

    public static final String BAD_CREDENTIALS = "Bad credentials";

    public BadCredentials() {
        super(BAD_CREDENTIALS);
    }

    public BadCredentials(String message) {
        super(message);
    }

    public BadCredentials(String message, Throwable ex) {
        super(message, ex);
    }
}