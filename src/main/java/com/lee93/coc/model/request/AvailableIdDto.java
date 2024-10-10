package com.lee93.coc.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableIdDto {

    @NotBlank( message = "ID를 입력해주세요.")
    @Size(min = 4, max = 11, message = "ID는 4~11자리만 가능합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d-_]{4,11}$",
            message = "ID는 영문과 숫자의 조합이며, 특수문자는 '-','_'만 가능합니다.")
    private String loginId;
}
