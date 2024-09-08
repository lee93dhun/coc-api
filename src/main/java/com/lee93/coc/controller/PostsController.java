package com.lee93.coc.controller;

import com.lee93.coc.model.entity.PostEntity;
import com.lee93.coc.model.request.RegisterPostRequestDto;
import com.lee93.coc.service.PostsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class PostsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService;

    @PostMapping(path = "/bbs/post")
    public ResponseEntity<String> resisterPost(@ModelAttribute RegisterPostRequestDto registerPostRequestDto){
        logger.info(" --- >>> Register Post request :: {}", registerPostRequestDto );
        PostEntity postEntity = PostEntity.builder()
                .categoryId(registerPostRequestDto.getCategoryId())
                .accountId(registerPostRequestDto.getAccountId())
                .postTitle(registerPostRequestDto.getPostTitle())
                .postContent(registerPostRequestDto.getPostContent())
                .build();
        postsService.registerPost(postEntity);
        return ResponseEntity.ok(" -- Register Post Success");
    }

}
