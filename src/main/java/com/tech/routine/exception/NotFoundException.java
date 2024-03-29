package com.tech.routine.exception;

public class NotFoundException extends BaseException{
    public NotFoundException(String objectName) {
        super(objectName + " Not Found", "NOT_FOUND");
    }
}
