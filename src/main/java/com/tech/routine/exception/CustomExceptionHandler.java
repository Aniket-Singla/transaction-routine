package com.tech.routine.exception;

import com.tech.routine.dto.ErrorResponse;
import com.tech.routine.dto.FieldErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({BaseException.class})
    public Mono<ResponseEntity<ErrorResponse>> handleAllCustomExceptions(BaseException exception, ServerWebExchange request) {
        var res = getErrorResponse(exception.getMessage(), exception.getErrorCode(), null, request);
        return Mono.just(new ResponseEntity<ErrorResponse>(res, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler({WebExchangeBindException.class})
    public Mono<ResponseEntity<ErrorResponse>> handleValidationError(WebExchangeBindException exception, ServerWebExchange request) {
        BindingResult result = exception.getBindingResult();
        List<FieldErrorResponse> fieldErrors = result
                .getFieldErrors()
                .stream()
                .map(
                        f ->
                                new FieldErrorResponse(
                                        f.getObjectName().replaceFirst("DTO$", ""),
                                        f.getField(),
                                        StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()
                                )
                )
                .toList();
        var res = getErrorResponse("Invalid Request Body", "INVALID_REQUEST_BODY", fieldErrors, request);
        return Mono.just(new ResponseEntity<ErrorResponse>(res, HttpStatus.BAD_REQUEST));
    }

    private static ErrorResponse getErrorResponse(String message, String code, List<FieldErrorResponse> fieldErrors, ServerWebExchange request) {
        return ErrorResponse.builder()
                .path(request.getRequest().getPath().toString())
                .requestId(request.getRequest().getId())
                .code(code)
                .status(HttpStatus.BAD_REQUEST.value())
                .error(message)
                .fieldErrors(fieldErrors)
                .build();
    }
}
