package com.lee93.coc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_ID(HttpStatus.CONFLICT,"이미 사용중인 아이디 입니다..");

    private final HttpStatus httpStatus;
    private final String message;

}
