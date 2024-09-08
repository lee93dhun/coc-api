package com.lee93.coc.model.request;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterPostRequestDto {
    private int categoryId;
    private String accountId;
    private String postTitle;
    private String postContent;
}
