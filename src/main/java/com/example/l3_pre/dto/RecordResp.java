package com.example.l3_pre.dto;

import com.example.l3_pre.dto.response.BaseDto;
import com.example.l3_pre.dto.response.RecordTypeResp;
import com.example.l3_pre.dto.response.StatusTypeResp;
import com.example.l3_pre.helper.DateHelper;
import com.example.l3_pre.validation.annotations.ValidDateValue;
import com.example.l3_pre.validation.annotations.ValidUserIdExists;
import com.example.l3_pre.validation.groups.OnCreate;
import com.example.l3_pre.validation.groups.OnSubmit;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ColumnResult;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.example.l3_pre.consts.MessageErrors.*;
import static com.example.l3_pre.consts.ValueConst.ID_MIN_VALUE;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecordResp implements BaseDto {

    private Integer id;
    private LocalDate submissionDate;
    private String submissionContent;
    private Integer createdBy;
    private String note;
    private Integer employeeId;
    private Integer leaderId;
    private LocalDate createdDate;
    private LocalDate rejectedDate;
    private String rejectedReason;
    private LocalDate approvedDate;
    private LocalDate appointmentDate;
    private String additionalRequest;
    private LocalDate endedDate;
    private String endedReason;
    private RecordTypeResp recordTypeResp;
    private StatusTypeResp statusTypeResp;

    public RecordResp(Integer id, LocalDate submissionDate, String submissionContent, Integer createdBy, String note, Integer employeeId, Integer leaderId, LocalDate createdDate, LocalDate rejectedDate, String rejectedReason, LocalDate approvedDate, LocalDate appointmentDate, String additionalRequest, LocalDate endedDate, String endedReason, Integer recordTypeId, Integer statusId, String recordTypeName, String statusName) {
        this.id = id;
        this.submissionDate = submissionDate;
        this.submissionContent = submissionContent;
        this.createdDate = createdDate;
        this.leaderId = leaderId;
        this.createdBy = createdBy;
        this.employeeId = employeeId;
        this.rejectedDate = rejectedDate;
        this.rejectedReason = rejectedReason;
        this.approvedDate = approvedDate;
        this.appointmentDate = appointmentDate;
        this.additionalRequest = additionalRequest;
        this.note = note;
        this.endedDate = endedDate;
        this.endedReason = endedReason;
        this.recordTypeResp = new RecordTypeResp(recordTypeId, recordTypeName);
        this.statusTypeResp = new StatusTypeResp(statusId, statusName);
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public String getSubmissionContent() {
        return submissionContent;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public String getNote() {
        return note;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getRejectedDate() {
        return rejectedDate;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public LocalDate getApprovedDate() {
        return approvedDate;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getAdditionalRequest() {
        return additionalRequest;
    }

    public LocalDate getEndedDate() {
        return endedDate;
    }

    public String getEndedReason() {
        return endedReason;
    }

    public RecordTypeResp getRecordTypeResp() {
        return recordTypeResp;
    }

    public StatusTypeResp getStatusTypeResp() {
        return statusTypeResp;
    }
}