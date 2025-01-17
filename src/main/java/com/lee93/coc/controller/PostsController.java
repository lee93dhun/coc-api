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

    /**
     * 게시물 등록 요청
     * @param authorizationHeader 로그인 인증 토큰
     * @param registerPostRequestDto 등록할 게시물의 데이터들을 포함한 객체
     * @param postsType 등록할 게시물의 게시판 타입
     * @return 게시물 등록 성공 응답 객체
     */
    @PostMapping(path = "/{postsType}/post")
    public ResponseEntity<SuccessResponse> resisterPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @ModelAttribute RegisterPostRequestDto registerPostRequestDto,
            @PathVariable(name = "postsType") String postsType){
        logger.info(" --- >>> Register Post request :: ", registerPostRequestDto );

        // TODO token 없을때 예외 처리
        String token = authorizationHeader.replace("Bearer","");
        String loginId = jwtTokenProvider.extractUserId(token);

        // TODO 게시물 등록 데티어 유효성검사

        PostEntity postEntity = PostMapper.INSTANCE.registerPostRequestDtoToPostEntity(registerPostRequestDto);
        postEntity.setAccountId(loginId);
        // TODO postsType 을 Dto 객체에 포함시키기 ?
        postEntity.setPostsType(PostsType.valueOf(postsType.toUpperCase()));

        postsService.registerPost(postEntity);

        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Register Post Successful")
                .build());
    }

}