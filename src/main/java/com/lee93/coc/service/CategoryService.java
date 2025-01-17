package com.lee93.coc.service;

import com.lee93.coc.dao.CategoryDao;
import com.lee93.coc.enums.PostsType;
import com.lee93.coc.exception.notFound.CategoryNotFoundException;
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

    /**
     * 게시판 타입에 해당하는 카테고리를 불러오는 기능
     * @param postsType 게시판 타입
     * @return 게시판 타입에 해당하는 카테고리 리스트 객체
     */
    public List<CategoryEntity> getCategoryList(String postsType) {
        logger.info("CategoryService -- getCategories() 실행");
        List<CategoryEntity> categoryEntityList =  categoryDao.getCategoryList(postsType);
        if(categoryEntityList == null || categoryEntityList.isEmpty()) {
            throw new CategoryNotFoundException(postsType);
        }
        return categoryEntityList;
    }
}
