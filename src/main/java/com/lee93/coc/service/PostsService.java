package com.lee93.coc.service;

import com.lee93.coc.dao.PostsDao;
import com.lee93.coc.model.entity.PostEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostsService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsDao postsDao;

    public void registerPost(PostEntity postEntity) {
        logger.info("PostsService -- registerPost() 실행");
        postsDao.registerPost(postEntity);
    }
}
