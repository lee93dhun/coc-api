package com.lee93.coc.exception.duplicate;

import com.lee93.coc.exception.ErrorCode;

public class LoginIdDuplicateException extends DuplicateException{

    public LoginIdDuplicateException(String loginId) {
        super(loginId, ErrorCode.DUPLICATE_ID);
    }
}
