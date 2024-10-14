package com.lee93.coc.model.entity;

import com.lee93.coc.enums.PostsType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostEntity {
    private int postId;
    private int categoryId;
    private String accountId;
    private String accountType;
    private String postTitle;
    private String postContent;
    private int postHits;
    private LocalDateTime postUploadAt;
    private PostsType postsType;
    
    private int isPinned;   // 공지사항 게시판
    private int isSecret;   // 문의 게시판

}
