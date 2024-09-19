package com.lee93.coc.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
//    private int userId;

    @NotBlank( message = "ID를 입력해주세요.")
    @Size(min = 4, max = 11, message = "ID는 4~11자리만 가능합니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d-_]{4,11}$"
            , message = "ID는 영문과 숫자의 조합이며, 특수문자는 '-','_'만 가능합니다.")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 4, max = 11, message = "비밀번호는 4~11자리만 가능합니다.")
    @Pattern(regexp = "^(?!.*([A-Za-z\\d!@#$%^&*])\\1{2})[A-Za-z\\d!@#$%^&*]+$"
            , message = "비밀번호는 영문, 숫자, 특수문자만 사용 가능하며, 연속된 문자가 3번 이상 반복될 수 없습니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 4, message = "이름은 2~5자리만 가능합니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "이름은 한글과 영문만 가능합니다.")
    private String userName;

    @NotNull
    private boolean availableSignup;
    
}
