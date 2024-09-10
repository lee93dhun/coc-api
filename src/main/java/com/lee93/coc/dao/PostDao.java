package com.lee93.coc.dao;

import com.lee93.coc.model.entity.PostEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDao {
    void registerPost(PostEntity postEntity);
}
