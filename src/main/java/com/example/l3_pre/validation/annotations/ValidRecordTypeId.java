package com.example.l3_pre.validation.annotations;

import com.example.l3_pre.validation.RecordTypeIdValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.l3_pre.consts.MessageErrors.*;

@Constraint(validatedBy = RecordTypeIdValidation.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRecordTypeId {
    String message() default RECORD_TYPE_ID_NOT_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
