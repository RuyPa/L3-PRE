package com.example.l3_pre.validation.annotations;

import com.example.l3_pre.validation.DateValueValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.l3_pre.consts.MessageErrors.INVALID_DATE_VALUE;

@Constraint(validatedBy = DateValueValidation.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateValue {
    String message() default INVALID_DATE_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
