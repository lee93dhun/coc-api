package com.lee93.coc.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileEntity {

    private int postId;
    private String originalName;
    private String saveName;
    private String filePath;
    private int fileSize;
}
