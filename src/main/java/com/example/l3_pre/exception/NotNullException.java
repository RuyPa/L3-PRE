package com.example.l3_pre.exception;

import com.example.l3_pre.suberror.ApiSubError;
import com.example.l3_pre.suberror.ErrorMessage;
import lombok.Getter;

@Getter
public class NotNullException extends L3Exception {

    private final ApiSubError apiSubError;

    public NotNullException(ErrorMessage errorMessage, ApiSubError apiSubError) {
        super(errorMessage);
        this.apiSubError = apiSubError;
    }
}
