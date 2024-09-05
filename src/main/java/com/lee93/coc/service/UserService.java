package com.lee93.coc.service;

import com.lee93.coc.dao.UserDao;
import com.lee93.coc.enums.ValidationStatus;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDao userDao;

    public String validateUserId(String loginId) {

        // 영어,숫자,특수문자('-','_')만 가능 / 공백 불가 / 4~11 글자
        String pattern = "^[a-zA-Z0-9_-]{4,11}$";
        if (loginId == null || !loginId.matches(pattern)) {
            return ValidationStatus.INVALID_FORMAT.name();
        }

        int duplicateResult =  userDao.duplicateId(loginId);
        if(duplicateResult > 0){
           return ValidationStatus.DUPLICATE.name();
        }
        return ValidationStatus.AVAILABLE.name();
    }
}
