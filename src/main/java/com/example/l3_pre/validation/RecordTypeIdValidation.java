package com.example.l3_pre.validation;

import com.example.l3_pre.validation.annotations.ValidRecordTypeId;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.l3_pre.consts.RecordType.*;

public class RecordTypeIdValidation implements ConstraintValidator<ValidRecordTypeId, Integer> {
    @Override
    public boolean isValid(Integer recordTypeId, ConstraintValidatorContext constraintValidatorContext) {
        if (recordTypeId == null) {
            return true;
        }
        return recordTypeId.equals(REGISTRATION.getId())
                || recordTypeId.equals(TERMINATION.getId());
    }
}
