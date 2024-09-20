package com.lee93.coc.service;

import com.lee93.coc.dao.CategoryDao;
import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CategoryDao categoryDao;

    public List<CategoryEntity> getCategories(String postsType) {
        logger.info("CategoryService -- getCategories() 실행");
        return categoryDao.getCategories(postsType);
    }
}
