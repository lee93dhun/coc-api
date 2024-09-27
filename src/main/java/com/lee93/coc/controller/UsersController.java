package com.lee93.coc.controller;

import com.lee93.coc.exception.CustomException;
import com.lee93.coc.exception.ErrorCode;
import com.lee93.coc.modelMappers.UserMapper;
import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.model.request.AvailableIdDto;
import com.lee93.coc.model.request.LoginRequestDto;
import com.lee93.coc.model.request.SignupRequestDto;
import com.lee93.coc.model.response.SuccessResponse;
import com.lee93.coc.service.UserService;
import com.lee93.coc.security.JwtTokenProvider;
import jakarta.validation.Valid;
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
     * @param availableIdDto sign up 요청하는 user 의 id
     * @return 사용가능한 id 일때 응답
     */
    // TODO 유효성 검사 실패시 message 안뜸
    @PostMapping(path = "/auth/available-loginid")
    public ResponseEntity<SuccessResponse> availableId(@RequestBody AvailableIdDto availableIdDto ) {
        logger.info(" --- >>> available user id : {}" , availableIdDto.getLoginId());
        String loginId = availableIdDto.getLoginId();
        userService.availableUserId(loginId);

        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("사용가능한 아이디입니다.")
                        .build());
    }

    /**
     * 회원가입
     * @param signupRequestDto 회원가입에 필요한 User data
     * @return 회원가입에 성공했을때의 응답
     */
    @PostMapping(path = "/user/signup")
    public ResponseEntity<SuccessResponse> signup( @RequestBody SignupRequestDto signupRequestDto ) {
        logger.info(" --- >>> Signup request received : {}", signupRequestDto.toString());
        UserEntity userEntity = UserEntity.builder()
                .loginId(signupRequestDto.getLoginId())
                .password(signupRequestDto.getPassword())
                .userName(signupRequestDto.getUserName())
                .build();
        userService.availableUserId(userEntity.getLoginId());
        userService.validateSignupData(userEntity);
        userService.signupUser(userEntity);

        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("회원가입이 완료되었습니다.")
                        .build());
    }

    /**
     * 로그인
     * @param loginRequestDto 로그인에 필요한 데이터
     * @return 로그인에 성공했을때의 응답
     */
    @PostMapping(path = "/user/login")
    public ResponseEntity<SuccessResponse> login(@RequestBody LoginRequestDto loginRequestDto){
        logger.info(" --- >>> Login request received : {}", loginRequestDto);

        UserEntity userEntity = UserMapper.INSTANCE.loginRequestToUserEntity(loginRequestDto);

        userService.loginUser(userEntity);

        String loginId = userEntity.getLoginId();
        String token = jwtTokenProvider.generateToken(loginId);

        return ResponseEntity.ok(SuccessResponse.builder()
                        .data(token)
                        .message("LOGIN SUCCESS")
                        .build());
    }

}
