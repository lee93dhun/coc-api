package com.lee93.coc.controller;

import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.CategoryEntity;
import com.lee93.coc.model.entity.PostEntity;
import com.lee93.coc.model.request.RegisterPostRequestDto;
import com.lee93.coc.model.response.CategoryResponseDto;
import com.lee93.coc.security.JwtTokenProvider;
import com.lee93.coc.service.CategoryService;
import com.lee93.coc.service.PostsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class PostsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService;
    private final CategoryService categoryService;
    private final JwtTokenProvider jwtTokenProvider;



    @PostMapping(path = "/bbs/post")
    public ResponseEntity<String> resisterPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute RegisterPostRequestDto registerPostRequestDto){
        logger.info(" --- >>> Register Post request :: {}", registerPostRequestDto );
        // TODO token 없을때 예외 처리
        String token = authorizationHeader.replace("Bearer","");
        String loginId = jwtTokenProvider.extractUserId(token);

        PostEntity postEntity = PostEntity.builder()
                .accountId(loginId)
                .categoryId(registerPostRequestDto.getCategoryId())
                .postTitle(registerPostRequestDto.getPostTitle())
                .postContent(registerPostRequestDto.getPostContent())
                .build();

        postsService.registerPost(postEntity);
        return ResponseEntity.ok(" -- Register Post Success");
    }

}