package com.library.error.exception;

import org.slf4j.helpers.MessageFormatter;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String messagePattern, Object... data) {
        super(MessageFormatter.format(messagePattern, data).getMessage());
    }
}
