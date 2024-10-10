package com.lee93.coc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE("C-001","Invalid Input Value"),
    RESOURCE_NOT_FOUND("C-002","Not Found"),

    REQUIRED_FIELD_EMPTY("","필수 입력값이 비어있습니다."),
    INVALID_FIELD_LENGTH("", "필드의 길이가 유효하지않은 값이 있습니다."),
    INVALID_FIELD_PATTERN("","유효하지 않은 입력값이 있습니다." ),

    // Authentication
    LOGIN_ID_NOT_FOUND("A-001","Login Id Not Found"),
    PASSWORD_MISMATCH("A-002","Password Mismatch"),
    // Duplicate
    DUPLICATE_ID("D-001","Duplicate ID");

    private final String code;
    private final String message;
}
