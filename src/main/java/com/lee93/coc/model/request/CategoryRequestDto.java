package com.lee93.coc.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    @NotBlank(message = "카테고리를 선택해주세요.")
    private String postsType;
}
