package com.lee93.coc.dao;

import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao {
    List<CategoryEntity> getCategories(String postsType);
}
