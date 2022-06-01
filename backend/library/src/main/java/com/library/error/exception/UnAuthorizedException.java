package com.library.error.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException() {
        super(UNAUTHORIZED.toString());
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException(String message, Throwable ex) {
        super(message, ex);
    }
}
