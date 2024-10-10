package com.lee93.coc.exception.duplicate;

import com.lee93.coc.exception.BusinessException;
import com.lee93.coc.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DuplicateException extends BusinessException {

    private String value;

    public DuplicateException(String value, ErrorCode errorCode) {
        super(value, errorCode);
        this.value = value;
    }
}
