package com.tech.routine.exception;

public class AlreadyExistException extends BaseException{
    public AlreadyExistException(String objectName) {
        super("Given " + objectName + " already exist!", "ALREADY_EXIST");
    }
}
