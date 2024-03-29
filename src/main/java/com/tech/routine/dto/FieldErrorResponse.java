package com.tech.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldErrorResponse {

    private final String objectName;

    private final String field;

    private final String message;
}
