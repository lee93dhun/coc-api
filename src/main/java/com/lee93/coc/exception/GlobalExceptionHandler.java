package com.lee93.coc.exception;

import com.lee93.coc.exception.duplicate.DuplicateException;
import com.lee93.coc.exception.notFound.ResourceNotFoundException;
import com.lee93.coc.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ce) {
        return ErrorResponse.toErrorResponse(ce.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final int status = HttpStatus.BAD_REQUEST.value();
        final ErrorResponse errorResponse = ErrorResponse.of(status, ErrorCode.INVALID_INPUT_VALUE,e.getBindingResult());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException e) {
        final int status = HttpStatus.NOT_FOUND.value();
        final ErrorCode errorCode = e.getErrorCode();
        final String value = e.getValue();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, value);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(final PasswordMismatchException e) {
        final int status = HttpStatus.UNAUTHORIZED.value();
        final ErrorCode errorCode = e.getErrorCode();
        final String value = e.getPassword();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, value);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(final DuplicateException e) {
        final int status = HttpStatus.CONFLICT.value();
        final ErrorCode errorCode = e.getErrorCode();
        final String value = e.getValue();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, value);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }








}
