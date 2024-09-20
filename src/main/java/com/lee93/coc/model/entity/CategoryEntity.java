package com.lee93.coc.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryEntity {
    private int categoryId;
    private String categoryName;
    private String postsType;
}
