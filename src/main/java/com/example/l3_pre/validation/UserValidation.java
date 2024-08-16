package com.example.l3_pre.validation;

import com.example.l3_pre.exception.InvalidInputException;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;

import static com.example.l3_pre.consts.MessageErrors.EMPLOYEE_ID_NOT_FOUND;
import static com.example.l3_pre.consts.ProcedureName.User.CHECK_USER_ID_EXISTENCE;
import static com.example.l3_pre.consts.ProcedureName.User.USER_ID;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final EntityManager entityManager;

    public boolean checkIdExists(Integer id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_USER_ID_EXISTENCE)
                .registerStoredProcedureParameter(USER_ID, Integer.class, ParameterMode.IN)
                .setParameter(USER_ID, id);
        return ((BigInteger) query.getSingleResult()).longValue() > 0;
    }

    public void isEmployeeExist(Integer employeeId) {
        if(!checkIdExists(employeeId)){
            throw new InvalidInputException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(EMPLOYEE_ID_NOT_FOUND));
        }
    }
}
