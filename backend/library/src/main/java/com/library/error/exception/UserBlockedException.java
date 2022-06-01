package com.library.error.exception;

import static com.library.util.Constant.USER_IS_BLOCKED;

public class UserBlockedException extends RuntimeException {

    public UserBlockedException() {
        super(USER_IS_BLOCKED);
    }

    public UserBlockedException(String message) {
        super(message);
    }

    public UserBlockedException(String message, Throwable ex) {
        super(message, ex);
    }
}

