package com.lee93.coc.service;

import com.lee93.coc.dao.UserDao;
import com.lee93.coc.exception.BusinessException;
import com.lee93.coc.exception.PasswordMismatchException;
import com.lee93.coc.exception.duplicate.LoginIdDuplicateException;
import com.lee93.coc.exception.notFound.LoginIdNotFoundException;
import com.lee93.coc.exception.unexpected.SignupUnexpectedException;
import com.lee93.coc.model.entity.UserEntity;
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
     */
    public void duplicateId(String loginId) {
        logger.info("-- duplicate login id service execute ");

         if (userDao.isAdminId(loginId) > 0 || userDao.duplicateId(loginId) > 0) {
            throw new LoginIdDuplicateException(loginId);
        }
    }

    /**
     * User 회원가입
     * @param userEntity 회원가입에 필요한 데이터
     */
    public void signupUser(UserEntity userEntity) {
        logger.info("-- signup user service execute ");
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            userDao.signupUser(userEntity);
        } catch (Exception e) {
            throw new SignupUnexpectedException();
        }

    }

    /**
     * 로그인을 하기 위한 메소드
     * loginId를 통해 암호화된 비밀번호를 DB 에서 가져와 비밀번호 확인
     * 요청한 loginId가 없다면 없는 사용자
     * @param userEntity 로그인에 필요한 데이터 (Id와 Pw 필드에만 값이있음)
     */
    public void loginUser(UserEntity userEntity) {
        logger.info("-- login user service execute ");
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
     * @param encodedPw DB 에서 가져온 암호화된 비밀번호
     * @return 비밀번호 일치 결과에 따른 boolean 값
     */
    public boolean checkPassword(String requestPw, String encodedPw){
        logger.info("-- check password service execute ");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(requestPw, encodedPw);
    }

}
