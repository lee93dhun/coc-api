package com.lee93.coc.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * StackTrace 기능을 수행하지 않음 (성능향상)
     * @return this
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
