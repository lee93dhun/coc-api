package com.lee93.coc.service;

import com.lee93.coc.dao.UserDao;
import com.lee93.coc.exception.CustomException;
import com.lee93.coc.exception.ErrorCode;
import com.lee93.coc.exception.PasswordMismatchException;
import com.lee93.coc.exception.duplicate.LoginIdDuplicateException;
import com.lee93.coc.exception.notFound.LoginIdNotFoundException;
import com.lee93.coc.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDao userDao;

    /**
     * 회원가입을 시도하는 ID의 중복검사 및 유효성 검사
     * @param loginId 회원가입을 시도하는 ID
     */
    public void availableUserId(String loginId) {
         if (userDao.isAdminId(loginId) > 0 || userDao.duplicateId(loginId) > 0) {
            throw new LoginIdDuplicateException(loginId);
        }
    }

    /**
     * User 회원가입
     * @param userEntity 회원가입에 필요한 데이터
     */
    public void signupUser(UserEntity userEntity) {
        logger.info("UserService  -- signupUser() 실행");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userDao.signupUser(userEntity);
    }

    /**
     * 로그인을 하기 위한 메소드
     * loginId를 통해 암호화된 비밀번호를 DB 에서 가져와 비밀번호 확인
     * 요청한 loginId가 없다면 없는 사용자
     * @param userEntity 로그인에 필요한 데이터 (Id와 Pw 필드에만 값이있음)
     */
    public void loginUser(UserEntity userEntity) {
        logger.info("UserService -- loginUser() 실행");
        String encodedPassword ;
        String loginId = userEntity.getLoginId();
        String requestPassword = userEntity.getPassword();

        encodedPassword = userDao.idCheckGetPassword(loginId);

        if (encodedPassword == null) {
            throw new LoginIdNotFoundException(loginId);
        }else if(!checkPassword(requestPassword, encodedPassword)){
            throw new PasswordMismatchException(requestPassword);
        }
    }

    /**
     * 요청한 비밀번호와 DB의 비밀번호 확인
     * @param requestPw 클라이언트에서 요청한 비밀번호
     * @param encodedPw DB에 저장되어 있는 암호화된 비밀번호
     * @return 일치하면 true, 일치하지 않다면 false
     */
    public boolean checkPassword(String requestPw, String encodedPw){
        logger.info(":: Password Check ::");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(requestPw, encodedPw);
    }

    /**
     * 회원가입시 입력한 데이터의 유효성 검사
     * @param userEntity 회원가입시 필요한 데이터
     */
    public void validateSignupData(UserEntity userEntity) {
        logger.info(":: Validate SignupData ::");

        if( userEntity.getPassword().isEmpty() || userEntity.getUserName().isEmpty()) {
            throw new CustomException(ErrorCode.REQUIRED_FIELD_EMPTY);
        }
        if((userEntity.getPassword().length() < 4 || userEntity.getPassword().length() > 12)
            || (userEntity.getUserName().length() < 2 || userEntity.getUserName().length() > 5) ) {
            throw new CustomException(ErrorCode.INVALID_FIELD_LENGTH);
        }
        if (!Pattern.matches("^(?!.*([A-Za-z\\d!@#$%^&*])\\1{2})[A-Za-z\\d!@#$%^&*]+$", userEntity.getPassword())
            || !Pattern.matches("^[A-Za-z가-힣]+$", userEntity.getUserName())
            || userEntity.getLoginId().equals(userEntity.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_FIELD_PATTERN);
        }
    }
}
