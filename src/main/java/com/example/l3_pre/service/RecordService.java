package com.example.l3_pre.service;

import com.example.l3_pre.dto.RecordResp;
import com.example.l3_pre.dto.request.*;

import java.util.List;

public interface RecordService {

    RecordResp create(RecordCreateUpdateDto req);

    RecordResp getByRecordId(Integer id);

    List<RecordResp> getAll(RecordSearchDto req);

    RecordResp update(Integer id, RecordCreateUpdateDto req);

    RecordResp submitToLeader(Integer id, RecordSubmissionDto req);

    RecordResp reject(Integer id, RecordRejectDto req);

    RecordResp approve(Integer id, RecordApproveDto req);

    RecordResp additionalRequest(Integer id, RecordAdditionalRequestDto req);

}
