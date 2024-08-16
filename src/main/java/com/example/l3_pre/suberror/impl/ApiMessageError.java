package com.example.l3_pre.suberror.impl;

import com.example.l3_pre.suberror.ApiSubError;
import lombok.Getter;

@Getter
public class ApiMessageError implements ApiSubError {
    private final String errorMessage;

    public ApiMessageError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
