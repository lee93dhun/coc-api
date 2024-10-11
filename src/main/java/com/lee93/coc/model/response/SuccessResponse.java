package com.lee93.coc.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SuccessResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

}
