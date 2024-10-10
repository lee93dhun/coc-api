package com.lee93.coc.exception.notFound;

import com.lee93.coc.exception.BusinessException;
import com.lee93.coc.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends BusinessException {
    private String value;

    public ResourceNotFoundException(String value, ErrorCode errorCode){
        super(value, errorCode);
        this.value = value;
    }
//    public ResourceNotFoundException(String value){
//        this(value, ErrorCode.NOT_FOUND);
//        this.value = value;
//    }
}
