package com.lee93.coc.controller;

import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.response.SuccessResponse;
import com.lee93.coc.service.FileService;
import com.lee93.coc.service.PostsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
public class FileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FileService fileService;
    private final PostsService postsService;

    @PostMapping(path = "{postsType}/{postId}/files")
    public ResponseEntity<SuccessResponse> uploadFiles(@PathVariable(name="postsType") final String postsType,
                                                        @PathVariable(name = "postId") final int postId,
                                                        @RequestPart final MultipartFile[] files){
        // TODO 로그인 인증 확인하기
        // TODO postId post 등록성공 후 클라이언트에서 전달 받기

        PostsType type = null;
        if(postsType.equals(PostsType.FREE.name().toLowerCase())){
            type = PostsType.FREE;
        }else if(postsType.equals(PostsType.GALLERY.name().toLowerCase())){
            type = PostsType.GALLERY;
        }

        fileService.validateFilesByType(type,files);
        fileService.saveFile(postId, files, type);

        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Files Upload Successful")
                .build());
    }
}
