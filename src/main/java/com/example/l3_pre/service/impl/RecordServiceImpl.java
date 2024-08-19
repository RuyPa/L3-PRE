package com.example.l3_pre.service.impl;

import com.example.l3_pre.consts.ProcedureName.Record;
import com.example.l3_pre.consts.RoleConst;
import com.example.l3_pre.dto.RecordResp;
import com.example.l3_pre.dto.request.*;
import com.example.l3_pre.dto.response.UserDetailsInfoResp;
import com.example.l3_pre.exception.NotAllowedException;
import com.example.l3_pre.service.RecordService;
import com.example.l3_pre.suberror.impl.ApiMessageError;
import com.example.l3_pre.suberror.impl.ErrorMessageConstant;
import com.example.l3_pre.utils.JsonUtils;
import com.example.l3_pre.validation.RecordValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

import static com.example.l3_pre.consts.Mapper.RECORD_MAPPER;
import static com.example.l3_pre.consts.MessageErrors.NOT_AUTHORIZED;
import static com.example.l3_pre.consts.ProcedureName.Record.MANAGER_ID;
import static com.example.l3_pre.consts.ProcedureName.Record.RECORD_ID;
import static com.example.l3_pre.consts.ProcedureName.User.LEADER_ID;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {

    private final EntityManager entityManager;
    private final RecordValidation recordValidation;

    @Override
    @Transactional
    public RecordResp create(RecordCreateUpdateDto req) {
        recordValidation.checkCreate(req);
        req.setCreatedBy(getUserIdFromAuthentication());
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.CREATE_RECORD, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_CREATE_UPDATE_JSON, String.class, ParameterMode.IN)
                .setParameter(Record.RECORD_CREATE_UPDATE_JSON, JsonUtils.convertToJson(req));
        return (RecordResp) query.getSingleResult();
    }

    @Override
    public RecordResp getByRecordId(Integer id) {
        switch (getCurrentRole()) {
            case RoleConst.ROLE_MANAGER:
                return getOneByManager(id);
            case RoleConst.ROLE_LEADER:
                return getOneByLeader(id);
            default:
                throw new NotAllowedException(ErrorMessageConstant.METHOD_NOT_ALLOWED,
                        new ApiMessageError(NOT_AUTHORIZED));
        }
    }

    private RecordResp getOneByLeader(Integer id) {
        recordValidation.checkGetOneByLeader(id);
        return getOneByRecordId(id);
    }

    private RecordResp getOneByManager(Integer id) {
        recordValidation.checkGetOneByManager(id);
        return getOneByRecordId(id);
    }

    private RecordResp getOneByRecordId(Integer id) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.GET_BY_RECORD_ID, RECORD_MAPPER)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(RECORD_ID, id);
        return (RecordResp) query.getSingleResult();
    }

    @Override
    public List<RecordResp> getAll(RecordSearchDto req) {
        recordValidation.checkGetAll(req);
        switch (getCurrentRole()) {
            case RoleConst.ROLE_MANAGER:
                return getAllByManager(req);
            case RoleConst.ROLE_LEADER:
                return getAllByLeader(req);
            default:
                throw new NotAllowedException(ErrorMessageConstant.METHOD_NOT_ALLOWED,
                        new ApiMessageError(NOT_AUTHORIZED));
        }
    }

    private List<RecordResp> getAllByLeader(RecordSearchDto req) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.GET_ALL_BY_LEADER, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_SEARCH_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(LEADER_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_SEARCH_JSON, JsonUtils.convertToJson(req))
                .setParameter(LEADER_ID, getUserIdFromAuthentication());
        return castResultSetToRecordRespList(query);
    }

    private List<RecordResp> getAllByManager(RecordSearchDto req) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.GET_ALL_BY_MANAGER, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_SEARCH_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(MANAGER_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_SEARCH_JSON, JsonUtils.convertToJson(req))
                .setParameter(MANAGER_ID, getUserIdFromAuthentication());
        return castResultSetToRecordRespList(query);
    }

    private List<RecordResp> castResultSetToRecordRespList(StoredProcedureQuery resultList) {
        List<RecordResp> result = new ArrayList<>();
        for (Object object : resultList.getResultList()) {
            result.add((RecordResp) object);
        }
        return result;
    }

    @Override
    @Transactional
    public RecordResp update(Integer id, RecordCreateUpdateDto req) {
        recordValidation.checkUpdate(id);
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.UPDATE_RECORD, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_CREATE_UPDATE_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_CREATE_UPDATE_JSON, JsonUtils.convertToJson(req))
                .setParameter(RECORD_ID, id);
        return (RecordResp) query.getSingleResult();
    }

    @Override
    @Transactional
    public RecordResp submitToLeader(Integer id, RecordSubmissionDto req) {
        recordValidation.checkSubmitToLeader(id);
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.SUBMIT_TO_LEADER, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_SUBMISSION_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_SUBMISSION_JSON, JsonUtils.convertToJson(req))
                .setParameter(RECORD_ID, id);
        return (RecordResp) query.getSingleResult();
    }

    @Override
    @Transactional
    public RecordResp reject(Integer id, RecordRejectDto req) {
        recordValidation.checkReject(id);
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.REJECT_RECORD, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_REJECT_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_REJECT_JSON, JsonUtils.convertToJson(req))
                .setParameter(RECORD_ID, id);
        return (RecordResp) query.getSingleResult();
    }

    @Override
    @Transactional
    public RecordResp approve(Integer id, RecordApproveDto req) {
        recordValidation.checkApprove(id);
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.APPROVE_RECORD, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_APPROVE_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_APPROVE_JSON, JsonUtils.convertToJson(req))
                .setParameter(RECORD_ID, id);
        return (RecordResp) query.getSingleResult();
    }

    @Override
    @Transactional
    public RecordResp additionalRequest(Integer id, RecordAdditionalRequestDto req) {
        recordValidation.checkAdditionalRequest(id);
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(Record.ADDITIONAL_REQUEST_RECORD, RECORD_MAPPER)
                .registerStoredProcedureParameter(Record.RECORD_ADDITIONAL_REQUEST_JSON, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(RECORD_ID, Integer.class, ParameterMode.IN)
                .setParameter(Record.RECORD_ADDITIONAL_REQUEST_JSON, JsonUtils.convertToJson(req))
                .setParameter(RECORD_ID, id);
        return (RecordResp) query.getSingleResult();
    }

    private Integer getUserIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsInfoResp userDetailsInfoResp = (UserDetailsInfoResp) authentication.getPrincipal();
        return userDetailsInfoResp.getId();
    }

    private String getCurrentRole() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();
    }
}
