package com.lee93.coc.exception.notFound;

import com.lee93.coc.exception.BusinessException;
import com.lee93.coc.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomNotFoundException extends BusinessException {
    private String value;

    public CustomNotFoundException(String value){
        this(value, ErrorCode.NOT_FOUND);
        this.value = value;
    }

    public CustomNotFoundException(String value, ErrorCode code){
        super(value, code);
        this.value = value;
    }
}
