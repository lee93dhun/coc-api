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

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
public class FileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FileService fileService;
    private final PostsService postsService;

    @PostMapping(path = "{postsType}/{postId}/files")
    public ResponseEntity<SuccessResponse> uploadFiles(@PathVariable(name="postsType") String postsType,
                                                        @PathVariable(name = "postId") int postId,
                                                        @RequestPart MultipartFile[] files){
        // TODO 로그인 인증 확인하기
        // TODO postId post 등록성공 후 클라이언트에서 전달 받기

        if(postsType.toUpperCase().equals(PostsType.FREE.name())){
            // TODO 파일 유효성 검사( 확장자 , 용량 , 갯수 )
            fileService.validateFileForFree(files);
        }else if(postsType.toUpperCase().equals(PostsType.GALLERY.name())){
            // TODO 갤러리 일때 유효성 검사 함수 실행
            fileService.validateFileForGallery(files);
        }

        for(MultipartFile file : files){
            if(!file.isEmpty()){
                fileService.saveFile(postId, file, PostsType.FREE);
            }
            else{
                // TODO 파일이 비어있거나 잘못된 파일일 경우 예외 던지기
            }
        }
        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Files Upload Successful")
                .build());
    }
}
