package com.lee93.coc.common;

import com.lee93.coc.enums.ResponseStatus;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    public final ResponseStatus status;

    public CustomException(final ResponseStatus status) {
        super(status.getMsg());
        this.status = status;
    }

}
