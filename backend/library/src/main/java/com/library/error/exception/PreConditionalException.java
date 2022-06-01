package com.library.error.exception;

import org.slf4j.helpers.MessageFormatter;

public class PreConditionalException extends RuntimeException {

    public PreConditionalException(String message) {
        super(message);
    }

    public PreConditionalException(String message, Throwable ex) {
        super(message, ex);
    }

    public PreConditionalException(String messagePattern, Object... data) {
        super(MessageFormatter.format(messagePattern, data).getMessage());
    }
}
