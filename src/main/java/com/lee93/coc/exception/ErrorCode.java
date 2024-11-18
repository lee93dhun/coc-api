package com.lee93.coc.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // TODO 코드분류 재설정 하기

    // Common
    INVALID_INPUT_VALUE("CM-001","Invalid input value"),
    RESOURCE_NOT_FOUND("CM-002","Not found"),
    UNEXPECTED_ERROR("CM-003", "Unexpected error"),

    // User 
    DUPLICATE_ID("US-001","Duplicate ID"),
    SIGNUP_UNEXPECTED_ERROR("US-002", "Unexpected error during user registration"),

    // Authentication
    LOGIN_ID_NOT_FOUND("AT-001","Login id not found"),
    PASSWORD_MISMATCH("AT-002","Password mismatch"),

    // Category
    CATEGORY_NOT_FOUND("CT-001", "Category not found"),

    ;


    private final String code;
    private final String message;
}
