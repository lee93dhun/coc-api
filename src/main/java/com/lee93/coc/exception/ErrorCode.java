package com.lee93.coc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE("C-001","Invalid Input Value"),
    NOT_FOUND("C-002","Not Found"),
    REQUIRED_FIELD_EMPTY("","필수 입력값이 비어있습니다."),
    INVALID_FIELD_LENGTH("", "필드의 길이가 유효하지않은 값이 있습니다."),
    INVALID_FIELD_PATTERN("","유효하지 않은 입력값이 있습니다." ),
    // Authentication
    LOGIN_ID_NOT_FOUND("A-001","Login Id Not Found"),
    PASSWORD_MISMATCH("A-002","Password Mismatch"),
    // USER
    DUPLICATE_ID("","사용할 수 없는 아이디 입니다."),
    USER_NOT_FOUND("","ID 또는 비밀번호를 확인해주세요.");

    private final String code;
    private final String message;
}
