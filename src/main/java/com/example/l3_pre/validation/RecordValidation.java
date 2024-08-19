package com.example.l3_pre.validation;

import com.example.l3_pre.consts.MessageErrors;
import com.example.l3_pre.consts.ProcedureName.Record;
import com.example.l3_pre.consts.RecordStatus;
import com.example.l3_pre.consts.RecordType;
import com.example.l3_pre.consts.ValueConst;
import com.example.l3_pre.dto.request.RecordCreateUpdateDto;
import com.example.l3_pre.dto.request.RecordSearchDto;
import com.example.l3_pre.dto.response.UserDetailsInfoResp;
import com.example.l3_pre.exception.InvalidInputException;
import com.example.l3_pre.exception.NotAllowedException;
import com.example.l3_pre.exception.NotNullException;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import com.example.l3_pre.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.math.BigInteger;

import static com.example.l3_pre.consts.MessageErrors.*;
import static com.example.l3_pre.consts.ProcedureName.Record.*;
import static com.example.l3_pre.consts.ProcedureName.User.EMPLOYEE_ID;
import static com.example.l3_pre.consts.ProcedureName.User.LEADER_ID;
import static com.example.l3_pre.consts.RecordType.REGISTRATION;
import static com.example.l3_pre.consts.RecordType.TERMINATION;
import static com.example.l3_pre.consts.ValueConst.FALSE_VALUE;

@Component
@RequiredArgsConstructor
public class RecordValidation {

    private final EntityManager entityManager;

    public void checkCreate(RecordCreateUpdateDto req) {
        checkEndedReason(req);
        checkExistRecordByEmployeeId(req);
    }

    private void checkExistRecordByEmployeeId(RecordCreateUpdateDto req) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_RECORD_CREATE_BY_EMPLOYEE_ID)
                .registerStoredProcedureParameter(Record.RECORD_CREATE_UPDATE_JSON, String.class, ParameterMode.IN)
                .setParameter(Record.RECORD_CREATE_UPDATE_JSON, JsonUtils.convertToJson(req));
        if (((BigInteger) query.getSingleResult()).longValue() > ValueConst.EXIST_VALUE){
            throw new InvalidInputException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(MessageErrors.RECORD_EXISTENCE));
        }
    }

    private void checkEndedReason(RecordCreateUpdateDto req) {
        if(req.getRecordTypeId().equals(RecordType.TERMINATION.getId())){
            isOfficialEmployee(req.getEmployeeId());
            if (ObjectUtils.isEmpty(req.getEndedReason())) {
                throw new NotNullException(ErrorMessageConstant.BAD_REQUEST,
                        new ApiMessageError(MessageErrors.ENDED_REASON_NOT_NULL));
            }
        } else {
            if(!ObjectUtils.isEmpty(req.getEndedReason())){
                throw new InvalidInputException(ErrorMessageConstant.BAD_REQUEST,
                        new ApiMessageError(ENDED_REASON_NOT_REQUIRED));
            }
        }
    }

    private void isOfficialEmployee(Integer employeeId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_OFFICIAL_EMPLOYEE)
                .registerStoredProcedureParameter(EMPLOYEE_ID, Integer.class, ParameterMode.IN)
                .setParameter(EMPLOYEE_ID, employeeId);
        if(((BigInteger) query.getSingleResult()).longValue() == FALSE_VALUE){
            throw new NotAllowedException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(NOT_OFFICIAL_EMPLOYEE));
        }
    }

    public void checkGetOneByLeader(Integer id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_GET_ONE_BY_LEADER)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(LEADER_ID, Integer.class, ParameterMode.IN)
                .setParameter(LEADER_ID, getUserIdFromAuthentication())
                .setParameter(RECORD_ID, id);
        if(((BigInteger) query.getSingleResult()).longValue() == FALSE_VALUE){
            throw new NotAllowedException(ErrorMessageConstant.METHOD_NOT_ALLOWED,
                    new ApiMessageError(NOT_AUTHORIZED));
        }
    }

    public void checkGetOneByManager(Integer id) {
        checkAuthorizedFromDatabase(id, CHECK_GET_ONE_BY_MANAGER);
    }

    public void checkUpdate(Integer id) {
        checkAuthorizedFromDatabase(id, CHECK_UPDATE);
    }

    private void checkAuthorizedFromDatabase(Integer id, String storedProcedureName) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(storedProcedureName)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(MANAGER_ID, Integer.class, ParameterMode.IN)
                .setParameter(RECORD_ID, id)
                .setParameter(MANAGER_ID, getUserIdFromAuthentication());
        if(((BigInteger) query.getSingleResult()).longValue() == FALSE_VALUE){
            throw new NotAllowedException(ErrorMessageConstant.METHOD_NOT_ALLOWED,
                    new ApiMessageError(NOT_AUTHORIZED));
        }
    }

    public void checkSubmitToLeader(Integer id) {
        if(!(getRecordStatusId(id)).equals(RecordStatus.NEW.getId())){
            throw new NotAllowedException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(NOT_AUTHORIZED));
        }
        checkAuthorizedFromDatabase(id, CHECK_GET_ONE_BY_MANAGER);
    }

    public void checkReject(Integer id) {
        checkGetOneByLeader(id);
    }

    public void checkApprove(Integer id) {
        checkGetOneByLeader(id);
    }

    public void checkAdditionalRequest(Integer id) {
        checkGetOneByLeader(id);
    }

    private Integer getRecordStatusId (Integer id){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(CHECK_RECORD_STATUS)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(RECORD_ID, id);
        return (Integer) query.getSingleResult();
    }

    public void checkGetAll(RecordSearchDto req) {
        if(ObjectUtils.isEmpty(req.getRecordTypeId())){
            throw new NotNullException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(RECORD_TYPE_ID_NOT_NULL));
        } else {
            boolean result = req.getRecordTypeId().equals(REGISTRATION.getId())
                    || req.getRecordTypeId().equals(TERMINATION.getId());
            if(!result){
                throw new InvalidInputException(ErrorMessageConstant.BAD_REQUEST,
                        new ApiMessageError(RECORD_TYPE_ID_NOT_VALID));
            }
        }
        if(ObjectUtils.isEmpty(req.getPageIndex())){
            throw new NotNullException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(PAGE_INDEX_NOT_NULL));
        }
        if(ObjectUtils.isEmpty(req.getPageSize())){
            throw new NotNullException(ErrorMessageConstant.BAD_REQUEST,
                    new ApiMessageError(PAGE_SIZE_NOT_NULL));
        }
    }

    private Integer getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsInfoResp userDetailsInfoResp = (UserDetailsInfoResp) authentication.getPrincipal();
        return userDetailsInfoResp.getId();
    }
}
