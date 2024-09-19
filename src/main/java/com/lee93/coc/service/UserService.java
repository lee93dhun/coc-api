package com.lee93.coc.service;

import com.lee93.coc.dao.UserDao;
import com.lee93.coc.enums.ResponseStatus;
import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.enums.ValidationStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDao userDao;

    /**
     * 회원가입을 시도하는 ID의 중복검사 및 유효성 검사
     * @param loginId 회원가입을 시도하는 ID
     * @return 유효성 검사 결과에 따른 응답 ENUM 값
     */
    public String availableUserId(String loginId) {
        // TODO loginId에 admin 이 포함되면 막기
        if (userDao.isAdminId(loginId) > 0) {
            return ValidationStatus.UNAVAILABLE.name();
        }
        if(userDao.duplicateId(loginId) > 0){
           return ValidationStatus.DUPLICATE.name();
        }
        return ValidationStatus.AVAILABLE.name();
    }
    
    public void signupUser(UserEntity userEntity) {
        logger.info("UserService  -- signupUser() 실행");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userDao.signupUser(userEntity);
    }

    public ResponseStatus loginUser(UserEntity userEntity) {
        logger.info("UserService -- loginUser() 실행");
        String encodedPassword ;
        encodedPassword = userDao.idCheckGetPassword(userEntity.getLoginId());
        if (encodedPassword == null) {
            return ResponseStatus.USER_NOT_FOUND;
        }
        boolean pwCheck = checkPassword(userEntity.getPassword(), encodedPassword);
        if(!pwCheck){
            return ResponseStatus.PASSWORD_MISMATCH;
        }
        return ResponseStatus.SUCCESS;
    }
    public boolean checkPassword(String requestPw, String encodedPw){
        logger.info(":: Password Check ::");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(requestPw, encodedPw);
    }
}
