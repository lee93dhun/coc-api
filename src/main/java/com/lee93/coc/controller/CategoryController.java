package com.lee93.coc.controller;

import com.lee93.coc.model.entity.CategoryEntity;
import com.lee93.coc.model.request.CategoryRequestDto;
import com.lee93.coc.model.response.CategoryResponseDto;
import com.lee93.coc.model.response.SuccessResponse;
import com.lee93.coc.modelMappers.CategoryMapper;
import com.lee93.coc.service.CategoryService;
import com.lee93.coc.service.PostsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api")
public class CategoryController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final CategoryService categoryService;

    @GetMapping(path="/categoryList")
    public ResponseEntity<SuccessResponse> getCategoryList(@RequestBody CategoryRequestDto categoryRequestDto){
        logger.info(" --- >>> Get Category Request :: {}", categoryRequestDto);

        List<CategoryEntity> categoryEntityList = categoryService.getCategoryList(categoryRequestDto.getPostsType());
        List<CategoryResponseDto> categoryResponseDtoList = CategoryMapper.INSTANCE.categoryEntitiesToCategoryDtos(categoryEntityList);
        return ResponseEntity.ok(SuccessResponse.builder()
                        .data(categoryResponseDtoList)
                        .message(categoryRequestDto.getPostsType()+" : 카테고리 List 불러오기 성공")
                        .build());
    }

}
