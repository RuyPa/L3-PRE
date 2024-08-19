package com.example.l3_pre.validation.annotations;

import com.example.l3_pre.validation.UserIdExistsValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.l3_pre.consts.MessageErrors.USER_NOT_EXIST;

@Constraint(validatedBy = UserIdExistsValidation.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserIdExists {
    String message() default USER_NOT_EXIST;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
