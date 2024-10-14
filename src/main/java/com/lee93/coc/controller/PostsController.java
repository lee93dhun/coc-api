package com.lee93.coc.controller;

import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.entity.PostEntity;
import com.lee93.coc.model.request.RegisterPostRequestDto;
import com.lee93.coc.model.response.SuccessResponse;
import com.lee93.coc.modelMappers.PostMapper;
import com.lee93.coc.security.JwtTokenProvider;
import com.lee93.coc.service.PostsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class PostsController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PostsService postsService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(path = "/bbs/post/{postsType}")
    public ResponseEntity<SuccessResponse> resisterPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute RegisterPostRequestDto registerPostRequestDto,
            @PathVariable(name = "postsType") String postsType){
        logger.info(" --- >>> Register Post request :: ", registerPostRequestDto );

        // TODO token 없을때 예외 처리
        String token = authorizationHeader.replace("Bearer","");
        String loginId = jwtTokenProvider.extractUserId(token);

        // TODO PostsType 변환실패시 예외처리
        PostEntity postEntity = PostMapper.INSTANCE.registerPostRequestDtoToPostEntity(registerPostRequestDto);
        postEntity.setAccountId(loginId);
        postEntity.setPostsType(PostsType.valueOf(postsType.toUpperCase()+"_BOARD"));

        postsService.registerPost(postEntity);

        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Register Post Successful")
                .build());
    }

}