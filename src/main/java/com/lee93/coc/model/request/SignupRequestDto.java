package com.lee93.coc.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
public class SignupRequestDto {

    @NotBlank
    @Size(min = 4, max = 11)
    @Pattern(regexp = "^(?=.*[A-Za-z])[A-Za-z\\d_-]+$",
            message = "영문과 숫자, 특수기호 [ -  _ ]만 가능합니다.")
    private String loginId;

    @NotBlank
    @Size(min = 4, max = 11)
    @Pattern(regexp = "^(?!.*([A-Za-z\\d!@#$%^&*])\\1{2})[A-Za-z\\d!@#$%^&*]+$",
            message = "연속된 숫자가 3번 반복될수 없습니다. 영문과 숫자, 특수기호 [!@#$%^&*] 만 가능합니다")
    private String password;

    @NotBlank
    @Size(min = 2, max = 5)
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "한글과 영어만 가능합니다.")
    private String userName;

}
