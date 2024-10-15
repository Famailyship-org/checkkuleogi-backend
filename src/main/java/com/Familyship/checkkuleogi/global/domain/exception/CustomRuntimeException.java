package com.Familyship.checkkuleogi.global.domain.exception;

import org.apache.logging.log4j.message.ParameterizedMessage;

public class CustomRuntimeException extends RuntimeException {
    public CustomRuntimeException(String message, Object... args) {
        super(ParameterizedMessage.format(message, args));
    }
}

