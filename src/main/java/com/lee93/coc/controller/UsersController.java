package com.lee93.coc.controller;

import com.lee93.coc.modelMappers.UserMapper;
import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.model.request.DuplicateIdRequestDto;
import com.lee93.coc.model.request.LoginRequestDto;
import com.lee93.coc.model.request.SignupRequestDto;
import com.lee93.coc.model.response.SuccessResponse;
import com.lee93.coc.service.UserService;
import com.lee93.coc.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class UsersController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 회원가입을 시도하는 ID가 사용가능한지 검사
     * @param duplicateIdRequestDto sign up 요청하는 user 의 id
     * @return 사용가능한 id 일때 응답
     */
    // TODO 유효성 검사 실패시 message 안뜸
    // TODO api 요청 url 재설정 하기
    @PostMapping(path = "/auth/available-loginid")
    public ResponseEntity<SuccessResponse> duplicateId(@Valid @RequestBody DuplicateIdRequestDto duplicateIdRequestDto) {
        logger.info(" --- >>> available user id : {}" , duplicateIdRequestDto.getLoginId());
        String loginId = duplicateIdRequestDto.getLoginId();
        userService.duplicateId(loginId);

        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("Available ID")
                        .build());
    }

    /**
     * 회원가입
     * @param signupRequestDto 회원가입에 필요한 User data
     * @return 회원가입에 성공했을때의 응답
     */
    @PostMapping(path = "/user/signup")
    public ResponseEntity<SuccessResponse> userSignup( @Valid @RequestBody SignupRequestDto signupRequestDto ) {
        logger.info(" --- >>> Signup request received : {}", signupRequestDto.toString());

        UserEntity userEntity = UserMapper.INSTANCE.signupRequestToUserEntity(signupRequestDto);

        userService.duplicateId(userEntity.getLoginId());
        userService.signupUser(userEntity);

        return ResponseEntity.ok(SuccessResponse.builder()
                        .message("User Sign Up Successful")
                        .build());
    }

    /**
     * 로그인
     * @param loginRequestDto 로그인에 필요한 데이터
     * @return 로그인에 성공했을때의 응답
     */
    @PostMapping(path = "/user/login")
    public ResponseEntity<SuccessResponse> userLogin(@Valid @RequestBody LoginRequestDto loginRequestDto){
        logger.info(" --- >>> Login request received : {}", loginRequestDto);

        UserEntity userEntity = UserMapper.INSTANCE.loginRequestToUserEntity(loginRequestDto);

        userService.loginUser(userEntity);

        String loginId = userEntity.getLoginId();
        String token = jwtTokenProvider.generateToken(loginId);

        return ResponseEntity.ok(SuccessResponse.builder()
                        .data(token)
                        .message("User Login Successful")
                        .build());
    }

}
