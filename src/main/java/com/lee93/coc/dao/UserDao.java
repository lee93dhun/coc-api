package com.lee93.coc.dao;

import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.model.request.LoginRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int duplicateId(String userId);

    int isAdminId(String loginId);

    void signupUser(UserEntity userEntity);

    String idCheckGetPassword(String loginId);
}
