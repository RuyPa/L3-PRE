package com.example.l3_pre.validation;

import com.example.l3_pre.exception.InvalidInputException;
import com.example.l3_pre.exception.NotNullException;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import static com.example.l3_pre.consts.MessageErrors.INVALID_STATUS_INPUT;
import static com.example.l3_pre.consts.MessageErrors.NOT_NULL_STATUS_VALUE;
import static com.example.l3_pre.consts.RecordStatus.*;

@Component
public class StatusValidation {

    public void checkValidStatusInput(String status) {
        isEmpty(status);
        boolean isValidStatus = status.equals(APPROVED.getStatusName())
                || status.equals(REJECTED.getStatusName())
                || status.equals(ADDITIONAL_REQUEST.getStatusName());
        if (!isValidStatus) {
            throw new InvalidInputException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(INVALID_STATUS_INPUT));
        }
    }

    private void isEmpty(String status) {
        if (ObjectUtils.isEmpty(status)) {
            throw new NotNullException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(NOT_NULL_STATUS_VALUE));
        }
    }
}
