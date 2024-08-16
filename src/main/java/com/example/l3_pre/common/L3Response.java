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
    private Integer code;
    private String message;
    private List<?> details;
    private T data;

    public static <T> L3Response<T> build(T data) {
        L3Response<T> response = new L3Response<>();
        response.data = data;
        response.code = ErrorMessageConstant.SUCCESS.getCode();
        return response;
    }

    public static <T> L3Response<T> build(ErrorMessage errorMessage) {
        L3Response<T> response = new L3Response<>();
        response.code = errorMessage.getCode();
        response.message = errorMessage.getMessage();
        return response;
    }

    public static <T> L3Response<T> build(ErrorMessage errorMessage, List<ApiSubError> details) {
        L3Response<T> response = build(errorMessage);
        response.details = details;
        return response;
    }
}
