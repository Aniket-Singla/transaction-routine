package com.tech.routine.exception;

public class ValidationException extends BaseException {
    public ValidationException(String message) {
        super(message, "INVALID_REQUEST");
    }
}
