package com.lee93.coc.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateIdRequestDto {

    @NotBlank( message = "ID를 입력해주세요.")
    @Size(min = 4, max = 11, message = "4 ~ 11 자리입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z\\d_-]+$",
            message = "영문과 숫자, 특수기호 [ -  _ ]만 가능합니다.")
    private String loginId;
}
