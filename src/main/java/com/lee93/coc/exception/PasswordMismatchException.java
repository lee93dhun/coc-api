package com.lee93.coc.exception;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends BusinessException{

    private String password;

    public PasswordMismatchException(final String password) {
        super(password, ErrorCode.PASSWORD_MISMATCH);
        this.password = password;
    }
}
