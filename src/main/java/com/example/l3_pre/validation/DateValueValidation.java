package com.example.l3_pre.validation;

import com.example.l3_pre.exception.InvalidInputException;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import com.example.l3_pre.validation.annotations.ValidDateValue;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class DateValueValidation implements ConstraintValidator<ValidDateValue, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (ObjectUtils.isEmpty(localDate)) {
            return true;
        }
        try {
            LocalDate today = LocalDate.now();
            int currentYear = Year.now().getValue();
            return localDate.isAfter(today) && localDate.getYear() == currentYear;
        } catch (DateTimeParseException exception) {
            throw new InvalidInputException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(exception.getMessage()));
        }
    }
}
