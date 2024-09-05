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


    // 아이디 중복확인
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
