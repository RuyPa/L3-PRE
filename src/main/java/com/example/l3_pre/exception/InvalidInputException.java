package com.example.l3_pre.exception;

import com.example.l3_pre.suberror.ApiSubError;
import com.example.l3_pre.suberror.ErrorMessage;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class InvalidInputException extends L3Exception {

    private final List<ApiSubError> apiSubErrors;

    public InvalidInputException(ErrorMessage errorMessage, ApiSubError apiSubError) {
        super(errorMessage);
        this.apiSubErrors = Collections.singletonList(apiSubError);
    }
}
