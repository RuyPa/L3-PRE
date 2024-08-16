package com.example.l3_pre.exception;

import com.example.l3_pre.suberror.ErrorMessage;
import lombok.Getter;

@Getter
public class L3Exception extends RuntimeException {
    private final ErrorMessage errorMessage;

    public L3Exception(ErrorMessage errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }
}
