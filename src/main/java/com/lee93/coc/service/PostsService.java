package com.lee93.coc.service;

import com.lee93.coc.dao.PostDao;
import com.lee93.coc.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostsService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostDao postDao;

    /**
     * 게시물을 등록하는 기능
     * @param postEntity 게시물 등록할 데이터들을 포함한 Entity 객체
     */
    public void registerPost(PostEntity postEntity) {
        logger.info("PostsService -- registerPost() 실행");
        postDao.registerPost(postEntity);
    }
}
