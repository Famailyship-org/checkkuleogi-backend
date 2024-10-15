package com.Familyship.checkkuleogi.global.domain.exception;

public class BadRequestException extends CustomRuntimeException {
    public BadRequestException(String message, Object... args) {
        super(message, args);
    }
}
