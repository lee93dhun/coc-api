package com.lee93.coc.controller;

import com.lee93.coc.dto.request.UserRequest;
import com.lee93.coc.service.UserService;
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


    /**
     * 회원가입을 시도하는 ID의 중복 검사 및 유효성 검사
     * @param loginId sign up 요청하는 user 의 id
     * @return 유효성 검사 결과에 따른 응답 ENUM 값
     */
    @PostMapping(path = "/auth/validate-loginid")
    public ResponseEntity<String> validateUser(@RequestParam String loginId) {
        logger.info(" --- >>> Validating user id : {}" , loginId);
        return ResponseEntity.ok(userService.validateUserId(loginId));
    }

    @PostMapping(path = "/auth/signup")
    public ResponseEntity<String> signup(@ModelAttribute UserRequest userRequest) {
        logger.info(" --- >>> Signup request received : {}", userRequest);
        // 비밀번호 암호화

        // 회원정보 저장

        return ResponseEntity.ok(" -- Signup successful");
    }

}
