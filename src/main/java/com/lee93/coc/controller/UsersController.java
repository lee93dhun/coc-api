package com.lee93.coc.controller;

import com.lee93.coc.common.CustomException;
import com.lee93.coc.common.GlobalExceptionHandler;
import com.lee93.coc.enums.ResponseStatus;
import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.model.request.LoginRequestDto;
import com.lee93.coc.model.request.SignupRequestDto;
import com.lee93.coc.service.UserService;
import com.lee93.coc.security.JwtTokenProvider;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class UsersController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 회원가입을 시도하는 ID가 사용가능한지 검사
     * @param loginId sign up 요청하는 user 의 id
     * @return 검사 결과에 따른 응답값
     */
    // TODO 유효성 검사 실패시 message 안뜸
    @GetMapping(path = "/auth/available-loginid")
    public ResponseEntity<String> availableId( @RequestParam
            @NotBlank( message = "ID를 입력해주세요.")
            @Size(min = 4, max = 11, message = "ID는 4~11자리만 가능합니다.")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d-_]{4,11}$", message = "ID는 영문과 숫자의 조합이며, 특수문자는 '-','_'만 가능합니다.")
            String loginId ) {
        logger.info(" --- >>> available user id : {}" , loginId);
        String availableResult = userService.availableUserId(loginId);
        return ResponseEntity.ok(availableResult);
    }

    /**
     * 회원가입
     * @param signupRequestDto 회원가입에 필요한 User data
     * @return
     */
    @PostMapping(path = "/user/signup")
    public ResponseEntity<String> signup(
            @Valid
            @RequestBody SignupRequestDto signupRequestDto ) {
        logger.info(" --- >>> Signup request received : {}", signupRequestDto.toString());
        // TODO api url 에 직접 접근시 return 고민
        if(!signupRequestDto.isAvailableSignup()) {
            return new ResponseEntity<>("잘못된 요청", HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = UserEntity.builder()
                .loginId(signupRequestDto.getLoginId())
                .password(signupRequestDto.getPassword())
                .userName(signupRequestDto.getUserName())
                .build();
        userService.signupUser(userEntity);

        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping(path = "/user/login")
    public ResponseEntity<Map> login(@RequestBody LoginRequestDto loginRequestDto){
        logger.info(" --- >>> Login request received : {}", loginRequestDto);

        UserEntity userEntity = UserEntity.builder()
                .loginId(loginRequestDto.getLoginId())
                .password(loginRequestDto.getPassword())
                .build();

        String loginId = userEntity.getLoginId();
        ResponseStatus result = userService.loginUser(userEntity);

        if(result.equals(ResponseStatus.SUCCESS)){
            String token = jwtTokenProvider.generateToken(loginId);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message","Login "+ResponseStatus.SUCCESS.getMsg());
           return ResponseEntity.ok(response);
        }else{
            throw new CustomException(result);
        }
    }



}
