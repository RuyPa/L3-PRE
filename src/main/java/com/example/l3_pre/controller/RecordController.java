package com.example.l3_pre.controller;

import com.example.l3_pre.common.L3Response;
import com.example.l3_pre.consts.RoleConst;
import com.example.l3_pre.dto.request.*;
import com.example.l3_pre.dto.RecordResp;
import com.example.l3_pre.service.RecordService;
import com.example.l3_pre.validation.annotations.ValidRecordIdExists;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@Validated
public class RecordController {

    private final RecordService recordService;

    @Secured({RoleConst.ROLE_MANAGER})
    @PostMapping()
    public L3Response<RecordResp> create(@RequestBody @Valid RecordCreateUpdateDto req){
        return new L3Response<>(recordService.create(req));
    }

    @Secured({RoleConst.ROLE_MANAGER, RoleConst.ROLE_LEADER})
    @GetMapping()
    public L3Response<List<RecordResp>> getAll(RecordSearchDto recordSearchDto){
        return new L3Response<>(recordService.getAll(recordSearchDto));
    }

    @Secured({RoleConst.ROLE_MANAGER, RoleConst.ROLE_LEADER})
    @GetMapping("/{id}")
    public L3Response<RecordResp> getById(@PathVariable("id") @ValidRecordIdExists Integer id){
        return new L3Response<>(recordService.getByRecordId(id));
    }

    @Secured({RoleConst.ROLE_MANAGER})
    @PostMapping ("/{id}")
    public L3Response<RecordResp> submitToLeader(@PathVariable("id") @ValidRecordIdExists Integer id,
                                          @RequestBody @Valid RecordSubmissionDto req){
        return new L3Response<>(recordService.submitToLeader(id, req));
    }

    @Secured({RoleConst.ROLE_MANAGER})
    @PutMapping("/{id}")
    public L3Response<RecordResp> update(@PathVariable("id") @ValidRecordIdExists Integer id,
                                         @RequestBody RecordCreateUpdateDto req){
        return new L3Response<>(recordService.update(id, req));
    }

    @Secured({RoleConst.ROLE_LEADER})
    @PostMapping("/{id}/reject")
    public L3Response<RecordResp> reject(@PathVariable("id") @ValidRecordIdExists Integer id,
                                         @RequestBody @Valid RecordRejectDto req){
        return new L3Response<>(recordService.reject(id, req));
    }

    @Secured({RoleConst.ROLE_LEADER})
    @PostMapping("/{id}/approve")
    public L3Response<RecordResp> approve(@PathVariable("id") @ValidRecordIdExists Integer id,
                                         @RequestBody @Valid RecordApproveDto req){
        return new L3Response<>(recordService.approve(id, req));
    }

    @Secured({RoleConst.ROLE_LEADER})
    @PostMapping("/{id}/additional-request")
    public L3Response<RecordResp> additionalRequest(@PathVariable("id") @ValidRecordIdExists Integer id,
                                                    @RequestBody @Valid RecordAdditionalRequestDto req){
        return new L3Response<>(recordService.additionalRequest(id, req));
    }
}
