package com.lee93.coc.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ThumbnailEntity {
    private int postId;
    private String thumbnailSaveName;
    private String thumbnailPath;
    private int thumbnailSize;
}
