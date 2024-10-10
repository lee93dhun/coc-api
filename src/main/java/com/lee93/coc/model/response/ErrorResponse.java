package com.lee93.coc.model.response;

import com.lee93.coc.exception.CustomFieldError;
import com.lee93.coc.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
//@Builder
@NoArgsConstructor
public class ErrorResponse <T>{
    private int status;
    private String code;
    private String message;
    private List<String> values = new ArrayList<>();

    private ErrorResponse(final int status, final ErrorCode code){
        this.status = status;
        this.code = code.toString();
        this.message = code.getMessage();
    }

    public static ErrorResponse of(int status, ErrorCode code, BindingResult bindingResult){
        ErrorResponse errorResponse = new ErrorResponse(status, code);
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            errorResponse.values.add(fieldError.getField()+" : "+fieldError.getDefaultMessage());
        }

        return errorResponse;
    }

    public static ErrorResponse of(int status, ErrorCode code, String value){
        ErrorResponse errorResponse = new ErrorResponse(status, code);
        errorResponse.values.add("Input Value : "+value);

        return errorResponse;
    }




    public static ErrorResponse of(int status, ErrorCode code){
        ErrorResponse errorResponse = new ErrorResponse(status, code);



        return errorResponse;
    }

    public static ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode ec){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.of(400,ErrorCode.INVALID_INPUT_VALUE));
    }

//    public static ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode ec , List<CustomFieldError> customFieldErrors){
//        return ResponseEntity
//                .status(ec.getHttpStatus())
//                .body(ErrorResponse.builder()
//                        .status(ec.getHttpStatus().value())
//                        .code(ec.name())
//                        .message(ec.getMessage())
//                        .data(customFieldErrors)
//                        .build());
//    }


}
