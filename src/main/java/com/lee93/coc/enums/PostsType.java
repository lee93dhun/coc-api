package com.lee93.coc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum PostsType {
    NOTICE,
    FREE(1024*5, 5, new String[]{"jpg", "gif", "png", "zip"} ),
    GALLERY(1024*3, 20, new String[]{"jpg", "gif", "png"} ),
    QNA;

    private final int limitFileSizeInKb;
    private final int limitFileCnt;
    private final String[] extensions;

    PostsType(int limitFileSizeInKb, int limitFileCnt, String[] extensions) {
        this.limitFileSizeInKb = limitFileSizeInKb;
        this.limitFileCnt = limitFileCnt;
        this.extensions = extensions;
    }
    PostsType(){
        this.limitFileSizeInKb = 0;
        this.limitFileCnt = 0;
        this.extensions = null;
    }
}
