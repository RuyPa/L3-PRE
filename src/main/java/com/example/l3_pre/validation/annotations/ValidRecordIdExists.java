package com.example.l3_pre.validation.annotations;

import com.example.l3_pre.validation.RecordIdValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.l3_pre.consts.MessageErrors.RECORD_ID_NOT_EXISTS;


@Constraint(validatedBy = RecordIdValidation.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRecordIdExists {
    String message() default RECORD_ID_NOT_EXISTS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}