package com.lee93.coc.exception.unexpected;

import com.lee93.coc.exception.BusinessException;
import com.lee93.coc.exception.ErrorCode;

public class SignupUnexpectedException extends BusinessException {
    public SignupUnexpectedException() {
        super("회원가입 도중 예기치 못한 오류 발생", ErrorCode.SIGNUP_UNEXPECTED_ERROR);
    }
}
