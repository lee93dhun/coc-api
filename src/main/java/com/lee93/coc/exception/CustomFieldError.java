package com.lee93.coc.exception;

import lombok.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CustomFieldError {

    private String field;
    private String value;
    private String reason;


    static List<CustomFieldError> of(final BindingResult bindingResult) {
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream()
                .map(error -> new CustomFieldError(
                        error.getField(),
                        error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @Builder
    public CustomFieldError(String field, String value, String reason) {
        this.field = field;
        this.value = value;
        this.reason = reason;
    }
}
