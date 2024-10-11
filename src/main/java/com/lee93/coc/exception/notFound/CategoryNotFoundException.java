package com.lee93.coc.exception.notFound;

import com.lee93.coc.exception.ErrorCode;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(final String postsType) {
        super(postsType, ErrorCode.CATEGORY_NOT_FOUND);
    }
}
