package com.lee93.coc.exception;

import com.lee93.coc.exception.duplicate.DuplicateException;
import com.lee93.coc.exception.notFound.ResourceNotFoundException;
import com.lee93.coc.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 예기치 못한 에러에 대한 예외 처리
     * @param e Exception
     * @return ErrorResponse 응답 객체
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        logger.error(":: Unexpected server error :: ", e);
        final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        final ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, "예기치 못한 오류");

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    /**
     * 비즈니스 로직 실행중 예기치 못한 오류 발생시 예외 처리
     * @param e BusinessException
     * @return message 를 포함한 ErrorResponse 객체
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
        final int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        final ErrorCode errorCode = e.getErrorCode();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    /**
     * 유효성 검사에 싫패에 대한 예외 처리
     * @param e MethodArgumentNotValidException
     * @return 유효성 검사 결과를 포함한 ErrorResponse 응답 객체
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final int status = HttpStatus.BAD_REQUEST.value();
        final ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, e.getBindingResult());

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    /**
     * 클라이언트에서 요청한 리소스나 데이터 값이 유효하지 않을 경우 예외 처리
     * @param e ResourceNotFoundException
     * @return ErrorResponse
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(final ResourceNotFoundException e) {
        final int status = HttpStatus.NOT_FOUND.value();
        final ErrorCode errorCode = e.getErrorCode();
        final String value = e.getValue();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, value);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    /**
     * password 가 일치 하지 않을 경우 예외 처리
     * @param e PasswordMismatchException
     * @return ErrorResponse
     */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponse> handlePasswordMismatchException(final PasswordMismatchException e) {
        final int status = HttpStatus.UNAUTHORIZED.value();
        final ErrorCode errorCode = e.getErrorCode();
        final String value = e.getPassword();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, value);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }

    /**
     * 중복검사 실패에 대한 예외 처리
     * @param e  DuplicateException
     * @return ErrorResponse
     */
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(final DuplicateException e) {
        final int status = HttpStatus.CONFLICT.value();
        final ErrorCode errorCode = e.getErrorCode();
        final String value = e.getValue();

        final ErrorResponse errorResponse = ErrorResponse.of(status, errorCode, value);

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(status));
    }








}
