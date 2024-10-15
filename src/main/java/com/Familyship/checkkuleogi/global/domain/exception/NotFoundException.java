package com.Familyship.checkkuleogi.global.domain.exception;

public class NotFoundException extends CustomRuntimeException {
  public NotFoundException(String message, Object... args) {
    super(message, args);
  }
}
