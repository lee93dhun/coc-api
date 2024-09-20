package com.lee93.coc.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponseDto {
    private int categoryId;
    private String categoryName;
    private String postsType;
}
