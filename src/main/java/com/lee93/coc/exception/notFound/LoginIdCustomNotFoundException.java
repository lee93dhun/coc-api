package com.lee93.coc.exception.notFound;

import com.lee93.coc.exception.ErrorCode;

public class LoginIdCustomNotFoundException extends CustomNotFoundException {

    public LoginIdCustomNotFoundException(final String loginId) {
        super(loginId, ErrorCode.LOGIN_ID_NOT_FOUND);
    }

}
