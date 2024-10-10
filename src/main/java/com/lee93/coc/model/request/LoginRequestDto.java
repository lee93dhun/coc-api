package com.lee93.coc.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @NotBlank(message = "ID를 입력해주세요.")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
