package com.example.l3_pre.validation;

import com.example.l3_pre.validation.annotations.ValidUserIdExists;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UserIdExistsValidation implements ConstraintValidator<ValidUserIdExists, Integer> {

    private final UserValidation userValidation;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        if (id == null) {
            return true;
        }
        return userValidation.checkIdExists(id);
    }
}