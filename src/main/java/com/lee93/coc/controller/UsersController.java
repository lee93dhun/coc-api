package com.lee93.coc.controller;

import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.model.request.LoginRequestDto;
import com.lee93.coc.model.request.SignupRequestDto;
import com.lee93.coc.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class UsersController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;


    /**
     * 회원가입을 시도하는 ID가 사용가능한지 검사
     * @param loginId sign up 요청하는 user 의 id
     * @return 검사 결과에 따른 응답값
     */
    // TODO 유효성 검사 실패시 message 안뜸
    @PostMapping(path = "/auth/available-loginid")
    public ResponseEntity<String> availableId( @RequestParam
            @NotBlank( message = "ID를 입력해주세요.")
            @Size(min = 4, max = 11, message = "ID는 4~11자리만 가능합니다.")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d-_]{4,11}$", message = "ID는 영문과 숫자의 조합이며, 특수문자는 '-','_'만 가능합니다.")
            String loginId ) {
        logger.info(" --- >>> available user id : {}" , loginId);
        String availableResult = userService.availableUserId(loginId);
        return ResponseEntity.ok(availableResult);
        // TODO 프론트엔드 - 응답 값에 따른 처리
    }

    /**
     * 회원가입
     * @param signupRequestDto 회원가입에 필요한 User data
     * @param availableSignUp 프론트에서 모든 항목에 유효성 검사를 한 결과 ( true , false )
     * @return
     */
    @PostMapping(path = "/user/signup")
    public ResponseEntity<String> signup(
            @Valid
            @ModelAttribute SignupRequestDto signupRequestDto,
            @RequestParam("availableSignUp") boolean availableSignUp) {
        logger.info(" --- >>> Signup request received : {}", signupRequestDto);
        // TODO 프론트엔드 - 비밀번호 확인 / 중복

        // TODO api url 에 직접 접근시 return 고민
        if(!availableSignUp) {
            return new ResponseEntity<>("잘못된 요청", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = UserEntity.builder()
                .loginId(signupRequestDto.getLoginId())
                .password(signupRequestDto.getPassword())
                .userName(signupRequestDto.getUserName())
                .build();
        userService.signupUser(userEntity);

        return ResponseEntity.ok(" -- Signup success");
    }

    @PostMapping(path = "/user/login")
    public ResponseEntity<String> login(@ModelAttribute LoginRequestDto loginRequestDto){
        logger.info(" --- >>> Login request received : {}", loginRequestDto);

        UserEntity userEntity = UserEntity.builder()
                .loginId(loginRequestDto.getLoginId())
                .password(loginRequestDto.getPassword())
                .build();

        boolean success = userService.loginUser(userEntity);
        // TODO success 결과에 따라 토큰 Access token 발급
        return ResponseEntity.ok(" -- Login success");
    }



}
