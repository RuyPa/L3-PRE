package com.example.l3_pre.common;

import com.example.l3_pre.suberror.ApiSubError;
import com.example.l3_pre.suberror.ErrorMessage;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class L3Response<T> {

    private final LocalDateTime localDateTime = LocalDateTime.now();
    private final Integer code;
    private String message;
    private List<?> details;
    private final T data;

    public L3Response(T data) {
        this.data = data;
        this.code = ErrorMessageConstant.SUCCESS.getCode();
    }

    public L3Response(ErrorMessage errorMessage, T data) {
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
        this.data = data;
    }

    public L3Response(ErrorMessage errorMessage, List<?> details) {
        this.code = errorMessage.getCode();
        this.message = errorMessage.getMessage();
        this.details = details;
        this.data = null;
    }
}
