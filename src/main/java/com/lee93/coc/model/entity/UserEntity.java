package com.lee93.coc.model.entity;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
public class UserEntity {
    private int userId;

    private String loginId;
    private String password;
    private String userName;
}
