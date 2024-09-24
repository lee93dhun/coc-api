package com.lee93.coc.modelMappers;

import com.lee93.coc.model.entity.UserEntity;
import com.lee93.coc.model.request.LoginRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userName", ignore = true)
    UserEntity loginRequestToUserEntity(LoginRequestDto loginRequestDto);
}
