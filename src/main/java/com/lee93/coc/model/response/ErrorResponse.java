package com.lee93.coc.model.response;

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

    /**
     * ErrorResponse 생성자
     * @param status 상태코드
     * @param code 오류유형 , 오류코드와 오류 메시지를 포함함
     */
    private ErrorResponse(final int status, final ErrorCode code){
        this.status = status;
        this.code = code.getCode();
        this.message = code.getMessage();
    }


    /**
     * 유효성 검사 결과를 기반으로 ErrorResponse 객체를 생성
     * BindingResult 에서 필드 오류를 처리하고 거부된 입력값을 포함함
     * @param status 응답에 포함될 HTTP 상태 코드
     * @param code 오류의 유형을 나타내는 ErrorCode, error message 를 포함하고 있음
     * @param bindingResult 유효성 검사 오류 결과
     * @return 유효성 검사 오류 세부 사항과 입력값을 포함한 ErrorResponse 객체
     */
    public static ErrorResponse of(int status, ErrorCode code, BindingResult bindingResult){
        ErrorResponse errorResponse = new ErrorResponse(status, code);

        for(FieldError fieldError : bindingResult.getFieldErrors()){
            String inputValue = fieldError.getRejectedValue() !=  null ? fieldError.getRejectedValue().toString() : null; ;
            errorResponse.values.add(fieldError.getField()+" : "+fieldError.getDefaultMessage()+" / Details : "+ inputValue);
        }

        return errorResponse;
    }

    /**
     * 상태 코드 , 오류 코드, 입력값을 기반으로 ErrorResponse 객체를 생성
     * @param status 응답에 포함될 HTTP 상태 코드
     * @param code 오류의 유형을 나타내는 ErrorCode, error message 를 포함하고 있음
     * @param value 클라이언트에서 입력된 값
     * @return 입력값이 포함된 ErrorResponse 객체
     */
    public static ErrorResponse of(int status, ErrorCode code, String value){
        ErrorResponse errorResponse = new ErrorResponse(status, code);
        errorResponse.values.add("Details : "+value);

        return errorResponse;
    }
}
