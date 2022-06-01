package com.library.error.exception;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String messagePattern, Object... data) {
        super(MessageFormatter.format(messagePattern, data).getMessage());
    }
}
