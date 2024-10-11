package com.lee93.coc.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends BusinessException{

    private String password;

    /**
     * password 가 일치 하지 않을 경우 예외 처리
     * @param password input password
     */
    public PasswordMismatchException(final String password) {
        super(password, ErrorCode.PASSWORD_MISMATCH);
        this.password = password;
    }
}
