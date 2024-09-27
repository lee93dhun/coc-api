package com.lee93.coc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // common
    REQUIRED_FIELD_EMPTY(HttpStatus.BAD_REQUEST,"필수 입력값이 비어있습니다."),
    INVALID_FIELD_LENGTH(HttpStatus.BAD_REQUEST, "필드의 길이가 유효하지않은 값이 있습니다."),
    INVALID_FIELD_PATTERN(HttpStatus.BAD_REQUEST,"유효하지 않은 입력값이 있습니다." ),
    // USER
    DUPLICATE_ID(HttpStatus.CONFLICT,"사용할 수 없는 아이디 입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"ID 또는 비밀번호를 확인해주세요."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST,"ID 또는 비밀번호를 확인해주세요.");

    private final HttpStatus httpStatus;
    private final String message;

}
