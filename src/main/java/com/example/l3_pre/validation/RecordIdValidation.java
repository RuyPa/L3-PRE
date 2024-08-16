package com.example.l3_pre.validation;

import com.example.l3_pre.validation.annotations.ValidRecordIdExists;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

import static com.example.l3_pre.consts.ProcedureName.Record.CHECK_RECORD_ID_EXISTENCE;
import static com.example.l3_pre.consts.ProcedureName.Record.RECORD_ID;
import static com.example.l3_pre.consts.ValueConst.FALSE_VALUE;

@RequiredArgsConstructor
public class RecordIdValidation implements ConstraintValidator<ValidRecordIdExists, Integer> {

    private final EntityManager entityManager;

    @Override
    public boolean isValid(Integer recordId, ConstraintValidatorContext constraintValidatorContext) {
        if(recordId == null){
            return true;
        }
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_RECORD_ID_EXISTENCE)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(RECORD_ID, recordId);
        return ((BigInteger) query.getSingleResult()).longValue() > FALSE_VALUE;
    }
}
