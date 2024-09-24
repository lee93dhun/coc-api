package com.lee93.coc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // USER
    DUPLICATE_ID(HttpStatus.CONFLICT,"이미 사용중인 아이디 입니다.."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"ID 또는 비밀번호를 확인해주세요."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST,"ID 또는 비밀번호를 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;

}
