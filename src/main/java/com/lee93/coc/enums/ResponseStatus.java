package com.lee93.coc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseStatus {
    SUCCESS("성공"),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다."),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다.");

    private final String msg;


}
