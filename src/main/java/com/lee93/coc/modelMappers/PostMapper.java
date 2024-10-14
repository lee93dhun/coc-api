package com.lee93.coc.modelMappers;

import com.lee93.coc.model.entity.CategoryEntity;
import com.lee93.coc.model.entity.PostEntity;
import com.lee93.coc.model.request.RegisterPostRequestDto;
import com.lee93.coc.model.response.CategoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostEntity registerPostRequestDtoToPostEntity(RegisterPostRequestDto registerPostRequestDto);
}
