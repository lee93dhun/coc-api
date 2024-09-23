package com.lee93.coc.model.response;

import com.lee93.coc.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorResponse {
    private int status;
    private String codeName;
    private String message;

    public static ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode ec){
        return ResponseEntity
                .status(ec.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(ec.getHttpStatus().value())
                        .codeName(ec.name())
                        .message(ec.getMessage())
                        .build());
    }
}
