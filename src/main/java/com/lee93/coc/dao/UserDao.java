package com.lee93.coc.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    int duplicateId(String userId);

    int isAdminId(String loginId);
}
