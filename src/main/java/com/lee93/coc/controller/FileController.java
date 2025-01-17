package com.lee93.coc.controller;

import com.lee93.coc.enums.PostsType;
import com.lee93.coc.model.response.SuccessResponse;
import com.lee93.coc.service.FileService;
import com.lee93.coc.service.PostsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping(path = "api")
@AllArgsConstructor
public class FileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FileService fileService;
    private final PostsService postsService;

    /**
     * 게시판 타입과 게시물 ID에 따른 파일 업로드 요청
     * @param postsType 업로드 할 파일들의 게시판 종류
     * @param postId 업로드 할 파일들의 게시물 id
     * @param files 업로드할 파일의 집합
     * @return 파일업로드 성공 응답 객체
     */
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
        }else{
            // TODO 두 타입에 해당되지 않는 요청시 "잘못된 요청" 예외 처리
        }
        fileService.validateFilesByType(type,files);
        fileService.saveFile(postId, files, type);

        return ResponseEntity.ok(SuccessResponse.builder()
                .message("Files Upload Successful")
                .build());
    }

    // TODO 파일 다운로드 기능 / 게시물 수정시 free, gallery 모두 0 / 게시물 상세보기 free 만 O
    @GetMapping(path = "/files/{fileIdList}/download")
    public ResponseEntity<SuccessResponse> downloadFiles(@PathVariable(name="fileIdList") final int[] fileIdList){
        Resource[] resources = new Resource[fileIdList.length];

        for(int i=0; i<fileIdList.length; i++){
            resources[i] = fileService.getFileByFileId(fileIdList[i]);
        }
            return ResponseEntity.ok(SuccessResponse.builder()
                .message("File Download Successful")
                .data(resources)
                .build());
    }
}
