package com.lee93.coc.controller;

import com.lee93.coc.exception.CustomException;
import com.lee93.coc.enums.ResponseStatus;
import com.lee93.coc.exception.ErrorCode;
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
     * @return 검사 결과에 따른 응답값
     */
    // TODO 유효성 검사 실패시 message 안뜸
    @PostMapping(path = "/auth/available-loginid")
    public ResponseEntity<SuccessResponse> availableId(@RequestBody AvailableIdDto availableIdDto ) {
        logger.info(" --- >>> available user id : {}" , availableIdDto.getLoginId());
        String loginId = availableIdDto.getLoginId();
        boolean isAvailable = userService.availableUserId(loginId);

        if(!isAvailable){
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }
        return ResponseEntity.ok(SuccessResponse.builder()
//                        .success(isAvailable)
                        .message("사용가능한 아이디입니다.")
                        .build());
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
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }
    }



}
