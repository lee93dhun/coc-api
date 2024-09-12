package com.lee93.coc.common;

import com.lee93.coc.enums.ResponseStatus;
import com.lee93.coc.model.response.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleException(CustomException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getStatus().name(), ex.getStatus().getMsg());
        return ResponseEntity.ok(errorResponseDto);
    }

}
