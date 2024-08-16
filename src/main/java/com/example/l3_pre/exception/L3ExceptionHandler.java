package com.example.l3_pre.exception;

import com.example.l3_pre.common.L3Response;
import com.example.l3_pre.suberror.ApiSubError;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.l3_pre.suberror.impl.ErrorMessageConstant.*;

@Slf4j
@ControllerAdvice
public class L3ExceptionHandler {

    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<L3Response<Object>> handleInvalidInputException(InvalidInputException exception) {
        log.info("handle invalid input exception: Msg = {}", exception.getErrorMessage().getMessage(), exception);
        return new ResponseEntity<>(L3Response.build(exception.getErrorMessage(),
                exception.getApiSubErrors()), HttpStatus.OK);
    }

    @ExceptionHandler(NotAllowedException.class)
    protected ResponseEntity<L3Response<Object>> handleNotAllowedException(NotAllowedException exception) {
        log.info("handle not allowed exception: Msg = {}", exception.getErrorMessage().getMessage(), exception);
        return new ResponseEntity<>(L3Response.build(exception.getErrorMessage(),
                Collections.singletonList(exception.getApiSubError())), HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<L3Response<Object>> handleNotFoundException(NotFoundException notFoundException) {
        log.info("handle not found exception: Msg = {}", notFoundException.getErrorMessage(), notFoundException);
        return new ResponseEntity<>(L3Response.build(notFoundException.getErrorMessage(),
                Collections.singletonList(notFoundException.getApiSubError())), HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<L3Response<Object>> handleConstraintViolationException(ConstraintViolationException exception) {
        String errorMessage = "";
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            errorMessage = violation.getMessage();
        }
        return new ResponseEntity<>(L3Response.build(NOT_FOUND,
                Collections.singletonList(new ApiMessageError(errorMessage))), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<L3Response<Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info("handle method argument not valid exception: Msg = {}", exception.getMessage(), exception);
        List<ApiSubError> subErrors = new ArrayList<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            subErrors.add(new ApiMessageError(fieldError.getDefaultMessage()));
        }
        return new ResponseEntity<>(L3Response.build(BAD_REQUEST, subErrors), HttpStatus.OK);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<L3Response<Object>> handleSqlException(SQLException exception) {
        log.info("handle sql exception : MSG = {}", exception.getMessage(), exception);
        ApiMessageError apiMessageError = new ApiMessageError(exception.getMessage());
        return new ResponseEntity<>(L3Response.build(BAD_REQUEST,
                Collections.singletonList(apiMessageError)), HttpStatus.OK);
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<L3Response<Object>> handleJwtException(JwtException exception) {
        log.info("handle jwt exception : MSG = {}", exception.getMessage(), exception);
        return new ResponseEntity<>(L3Response.build(UNAUTHORIZED,
                Collections.singletonList(new ApiMessageError(exception.getMessage()))), HttpStatus.OK);
    }

    @ExceptionHandler(NotNullException.class)
    protected ResponseEntity<L3Response<Object>> handleNotNullException(NotNullException exception) {
        log.info("handle not null exception : MSG = {}", exception.getMessage(), exception);
        return new ResponseEntity<>(L3Response.build(exception.getErrorMessage(),
                Collections.singletonList(exception.getApiSubError())), HttpStatus.OK);
    }
}
